package komponentowe.zadanie2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    private final Connection cnct;
    private final String sudokuName;

    public JdbcSudokuBoardDao(String sudokuName) throws SQLException {
        this.sudokuName = sudokuName;
        cnct = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sudoku", "postgres", "admin");
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException, SQLException {
        PreparedStatement getSudokuStatement = cnct.prepareStatement("select * from sudokus where NAME=?");
        PreparedStatement getFieldsStatement = cnct.prepareStatement("select fields from fields where \"ID\"=?");


        getSudokuStatement.setString(1, sudokuName);
        ResultSet row = getSudokuStatement.executeQuery();
        if(row.next()){
            int fieldsid = row.getInt(2);

            getFieldsStatement.setInt(1,fieldsid);
            ResultSet fieldsResult = getFieldsStatement.executeQuery();
            if (fieldsResult.next()){
                var output = fieldsResult.getArray(1);
                Integer[] arr = (Integer[]) output.getArray();
                var iterator = Arrays.stream(arr).iterator();
                for (var elem : arr) {
                    SudokuField[][] fields = new SudokuField[9][9];
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            fields[i][j] = new SudokuField(iterator.next());
                            System.out.println(fields[i][j]);

                        }
                    }
                    SudokuSolver solver = new BacktrackingSudokuSolver();
                    SudokuBoard board = new SudokuBoard(fields, solver);
                    System.out.println(board);
                    return board;
                }
            }
        }
        return null;
    }

    @Override
    public void write(SudokuBoard obj) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }
}
