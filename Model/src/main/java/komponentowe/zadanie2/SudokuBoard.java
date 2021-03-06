package komponentowe.zadanie2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SudokuBoard implements Serializable, Cloneable {

    private static final Logger logger = LogManager.getLogger(SudokuBoard.class);

    private SudokuField[][] board;
    private ArrayList<SudokuObserver> sudokuObservers = new ArrayList<>();
    SudokuSolver solver;


    public SudokuBoard(SudokuField[][] board, SudokuSolver solver) {
        this.board = board;
        this.solver = solver;
    }

    public ArrayList<SudokuObserver> getSudokuObservers() {
        return sudokuObservers;
    }

    //    debugging purposes
    //
    //    public static String get2DArrayPrint(SudokuField[][] matrix) {
    //        StringBuilder output = new StringBuilder();
    //        for (SudokuField[] sudokuFields : matrix) {
    //            for (SudokuField sudokuField : sudokuFields) {
    //                output.append(sudokuField.getFieldValue()).append("\t");
    //            }
    //            output.append("\n");
    //        }
    //        return output.toString();
    //    }

    public void register(SudokuObserver o) {
        if (!sudokuObservers.contains(o)) {
            sudokuObservers.add(o);
        }
    }

    public void unregister(SudokuObserver o) {
        sudokuObservers.remove(o);
    }

    public void notifyObservers() {
        for (SudokuObserver sudokuObserver : sudokuObservers
        ) {
            sudokuObserver.update(board);
        }
    }

    public boolean isFull() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (get(row, column) == 0) {
                    logger.debug("board is not full");
                    return false;
                }
            }
        }
        logger.debug("board is full");
        return true;
    }

    public boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!(getColumn(i).verify() && getRow(i).verify())) {
                logger.debug("board is not correctly filled");
                return false;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        logger.debug("board is correctly filled");
        return true;
    }

    public void solveGame() throws WrongSudokuStateException {
        solver.solve(this);
        if (!checkBoard()) {
            throw new WrongSudokuStateException("Incorrect board generated...");
        }
    }

    public void set(int x, int y, int value) throws IllegalArgumentException {
        if ((x < 9 && x >= 0) && (y < 9 && y >= 0) && (value <= 9 && value >= 0)) {
            board[y][x].setFieldValue(value);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Wrong coordinates or value");
        }
    }

    public int get(int x, int y) throws IllegalArgumentException {
        if (x < 9 && y < 9 && x >= 0 && y >= 0) {
            return board[y][x].getFieldValue();
        } else {
            throw new IllegalArgumentException("Wrong coordinates...");
        }
    }

    public SudokuRow getRow(int y) {
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        //ArrayList<SudokuField> fields = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField(board[y][i]));
        }
        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> column = Arrays.asList(new SudokuField[9]);
        //ArrayList<SudokuField> column = new ArrayList<>(9);
        for (int y = 0; y < 9; y++) {
            column.set(y, new SudokuField(board[y][x]));
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {
        List<SudokuField> box = Arrays.asList(new SudokuField[9]);
        int squareRow = y / 3;
        int squareColumn = x / 3;
        int[] rowsToIterate = new int[3];
        int[] columnsToIterate = new int[3];
        for (int i = 0; i < 3; i++) {
            rowsToIterate[i] = squareRow * 3 + i;
            columnsToIterate[i] = squareColumn * 3 + i;
        }
        int i = 0;
        for (int row : rowsToIterate
        ) {
            for (int column : columnsToIterate
            ) {
                box.set(i, new SudokuField(board[row][column]));
                i++;
            }
        }
        return new SudokuBox(box);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard)) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder()
                .append(board, that.board)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board)
                .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                builder.append(this.get(x,y));
                builder.append(' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }


    // deep copy ( sudokuBoard fields can change so we need deep copy )
    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard copy = (SudokuBoard) super.clone();
        copy.board = new SudokuField[9][9];
        for (int i = 0; i < copy.board.length; i++) {
            copy.board[i] = new SudokuField[copy.board.length];
            for (int j = 0; j < 9; j++) {
                copy.board[i][j] = this.board[i][j].clone();
            }
        }

        copy.sudokuObservers = new ArrayList<>();
        // observer wanted to observe one sudokuBoard, not many
        // don't suprise them :)

        // solver can be copied by reference, it has no fields, only behaviour
        copy.solver = new BacktrackingSudokuSolver();

        return copy;
    }

    public SudokuField[][] getBoard() {
        return board;
    }
}

