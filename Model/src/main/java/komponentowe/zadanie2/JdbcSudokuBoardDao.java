package komponentowe.zadanie2;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class JdbcSudokuBoardDao implements Dao<SaveObject> {

    private final Connection cnct;
    private final String sudokuName;

    public JdbcSudokuBoardDao(String sudokuName) throws SQLException {
        this.sudokuName = sudokuName;
        cnct = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sudoku",
                "postgres", "admin");

    }

    @Override
    public SaveObject read() throws SudokuSQLException {
        try {
            cnct.setAutoCommit(true);
            PreparedStatement getSudokuStatement = cnct.prepareStatement("select * from sudokus where NAME=?");
            PreparedStatement getFieldsStatement = cnct.prepareStatement("select fields from fields where \"ID\"=?");
            PreparedStatement getOriginalFieldsStatement = cnct.prepareStatement("select fields from \"originalFields\" where \"ID\"=?");


            getSudokuStatement.setString(1, sudokuName);
            ResultSet row = getSudokuStatement.executeQuery();
            if (row.next()) {
                int fieldsid = row.getInt(2);
                int originalFieldsid = row.getInt(3);

                getFieldsStatement.setInt(1, fieldsid);
                getOriginalFieldsStatement.setInt(1, originalFieldsid);

                // create current board:
                ResultSet fieldsResult = getFieldsStatement.executeQuery();
                if (fieldsResult.next()) {
                    var output = fieldsResult.getArray(1);
                    Integer[] arr = (Integer[]) output.getArray();
                    var iterator = Arrays.stream(arr).iterator();
                    SudokuField[][] fields = new SudokuField[9][9];
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            fields[j][i] = new SudokuField(iterator.next());

                        }
                    }

                    SudokuBoard currentBoard = new SudokuBoard(fields, new BacktrackingSudokuSolver());

                    // create originalBoard
                    ResultSet originalFieldsResult = getOriginalFieldsStatement.executeQuery();
                    if (originalFieldsResult.next()) {
                        var outputOriginal = originalFieldsResult.getArray(1);
                        Integer[] arrOriginal = (Integer[]) outputOriginal.getArray();
                        var iteratorOriginal = Arrays.stream(arrOriginal).iterator();
                        SudokuField[][] fieldsOriginal = new SudokuField[9][9];
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                fieldsOriginal[j][i] = new SudokuField(iteratorOriginal.next());

                            }
                        }

                        SudokuBoard originalBoard = new SudokuBoard(fieldsOriginal, new BacktrackingSudokuSolver());

                        return new SaveObject(currentBoard, originalBoard);

                    }
                    originalFieldsResult.close();
                }
                fieldsResult.close();
            }
            row.close();
            return null;
        } catch (SQLException throwables) {
            throw new SudokuSQLException("reading failed");
        }


    }

    public void write(SaveObject obj) throws SudokuSQLException {
        try {
            cnct.setAutoCommit(false);
            PreparedStatement createFieldsStatement = cnct.prepareStatement("insert into fields " +
                    " (fields)" +
                    " values (?)", Statement.RETURN_GENERATED_KEYS);

            PreparedStatement createOriginalFieldsStatement = cnct.prepareStatement("insert into \"originalFields\" " +
                    " (fields)" +
                    " values (?)", Statement.RETURN_GENERATED_KEYS);


            PreparedStatement createSudokuMainTableStatement = cnct.prepareStatement("insert into sudokus " +
                    " values (?, ?, ?)");


            // create fields row
            Array arrForFields = cnct.createArrayOf("INTEGER", createArrayFromSudoku(obj.getCurrent()));

            createFieldsStatement.setArray(1, arrForFields);

            int affectedRowsInFields = createFieldsStatement.executeUpdate();
            if (affectedRowsInFields == 0) {
                cnct.rollback();
                throw new NoRowsAffectedException("Creating row with fields failed");
            }

            try (ResultSet generatedKeysFields = createFieldsStatement.getGeneratedKeys()) {
                if (generatedKeysFields.next()) {
                    int idInFields = generatedKeysFields.getInt(2);
                    // create original fields row
                    Array arrForOriginalFields = cnct.createArrayOf("INTEGER", createArrayFromSudoku(obj.getOriginal()));

                    createOriginalFieldsStatement.setArray(1, arrForOriginalFields);

                    int affectedRowsInOriginalFields = createOriginalFieldsStatement.executeUpdate();
                    if (affectedRowsInOriginalFields == 0) {
                        cnct.rollback();
                        throw new NoRowsAffectedException("Creating row with fields failed");
                    }

                    try (ResultSet generatedKeys = createOriginalFieldsStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idInOriginalFields = generatedKeys.getInt(2);


                            // create sudoku row with references
                            createSudokuMainTableStatement.setString(1, sudokuName);
                            createSudokuMainTableStatement.setInt(2, idInFields);
                            createSudokuMainTableStatement.setInt(3, idInOriginalFields);

                            createSudokuMainTableStatement.executeUpdate();
                            cnct.commit();

                        } else {
                            cnct.rollback();
                            throw new NoKeyGeneratedException("Creating fields failed, no ID obtained.");
                        }
                    }

                } else {
                    cnct.rollback();
                    throw new SQLException("Creating fields failed, no ID obtained.");
                }
            }
        } catch (SQLException throwables) {
            throw new SudokuSQLException("something went wrong while writing");
        }

    }


    public void delete() throws SQLException {
        cnct.setAutoCommit(false);
        PreparedStatement getIDsFromSudokus = cnct.prepareStatement("SELECT \"fieldsRef\", \"originalFieldsRef\" from sudokus" +
                " where name=?");

        PreparedStatement deleteFromSudokusStatement = cnct.prepareStatement("DELETE from sudokus where name=?");

        PreparedStatement deleteFromFields = cnct.prepareStatement("DELETE from fields where \"ID\"=?");

        PreparedStatement deleteFromOriginalFields = cnct.prepareStatement("DELETE from \"originalFields\" where \"ID\"=?");

        //1. get 2 IDS

        getIDsFromSudokus.setString(1, sudokuName);
        ResultSet twoIDsSet = getIDsFromSudokus.executeQuery();
        if (twoIDsSet.next()) {
            int fieldsRef = twoIDsSet.getInt(1);
            int originalFieldsRef = twoIDsSet.getInt(2);
            // 2. usuniecie z głownej tabeli
            deleteFromSudokusStatement.setString(1, sudokuName);
            deleteFromSudokusStatement.execute();

            // 3. usuniecie z tabeli fields
            deleteFromFields.setInt(1, fieldsRef);
            deleteFromFields.execute();

            // 4. usuniecie z tabeli original fields
            deleteFromOriginalFields.setInt(1, originalFieldsRef);
            deleteFromOriginalFields.execute();
            cnct.commit();

        } else {
            cnct.rollback();
            throw new SudokuSQLException("didn't find row to delete");
        }
        twoIDsSet.close();
    }

    public void update(SaveObject saveObject) throws SudokuSQLException {
        try {
            cnct.setAutoCommit(false);
            // 1. prepare statements

            PreparedStatement updateFieldsStatement = cnct.prepareStatement("UPDATE fields" +
                    " SET fields=?" +
                    " WHERE \"ID\"=?");

            PreparedStatement updateOriginalFieldsStatement = cnct.prepareStatement("UPDATE \"originalFields\" " +
                    " SET fields=?" +
                    " WHERE \"ID\"=?");

            PreparedStatement getIDsFromSudokus = cnct.prepareStatement("SELECT \"fieldsRef\", \"originalFieldsRef\" from sudokus" +
                    " where name=?");

            // 2. get ids

            getIDsFromSudokus.setString(1, sudokuName);
            ResultSet twoIDsSet = getIDsFromSudokus.executeQuery();
            if (twoIDsSet.next()) {
                int fieldsRef = twoIDsSet.getInt(1);
                int originalFieldsRef = twoIDsSet.getInt(2);

                // 3. update fields table
                updateFieldsStatement.setArray(1, cnct.createArrayOf("INTEGER", createArrayFromSudoku(saveObject.getCurrent())));
                updateFieldsStatement.setInt(2, fieldsRef);

                updateFieldsStatement.execute();

                // 4. update originalFields table

                updateOriginalFieldsStatement.setArray(1, cnct.createArrayOf("INTEGER", createArrayFromSudoku(saveObject.getOriginal())));
                updateOriginalFieldsStatement.setInt(2, originalFieldsRef);

                updateOriginalFieldsStatement.execute();
                cnct.commit();

            } else {
                cnct.rollback();
                throw new SudokuSQLException("didn't found row to alter");
            }
            twoIDsSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new SudokuSQLException();
        }

    }


    @Override
    public void close() throws Exception {
        cnct.close();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    private Integer[] createArrayFromSudoku(SudokuBoard board) {
        List<Integer> linkedList = new LinkedList();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                linkedList.add(board.get(i, j));
            }
        }
        Integer[] out = new Integer[81];
        linkedList.toArray(out);
        return out;
    }

    public String[] getSudokuNames() throws SudokuSQLException {
        try{
            Statement statement = cnct.createStatement();
            ResultSet myRs = statement.executeQuery("select name from sudokus");
            LinkedList<String> names = new LinkedList<>();
            while(myRs.next()){
                names.add(myRs.getString(1));
            }
            myRs.close();
            return names.toArray(new String[0]);
        } catch (SQLException e) {
            throw new SudokuSQLException("can't get sudoku names");
        }

    }

}
