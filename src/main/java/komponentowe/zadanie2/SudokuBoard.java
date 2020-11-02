package komponentowe.zadanie2;


import java.util.Arrays;


public class SudokuBoard {

    private final SudokuField[][] board;
    SudokuSolver solver;

    public SudokuBoard(SudokuField[][] board, SudokuSolver solver) {
        this.board = board;
        this.solver = solver;
    }

    boolean isFull() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (get(row, column) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!(getColumn(i).verify() && getRow(i).verify())) {
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
            board[y][x] = new SudokuField(value);
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



    // debugging purposes method
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

    /**
     * Used for testing equality of SudokuBoards used in tests.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return Arrays.deepEquals(board, that.board);
    }

    public SudokuRow getRow(int y) {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField(board[y][i]);
        }
        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] column = new SudokuField[9];
        for (int y = 0; y < 9; y++) {
            column[y] = new SudokuField(board[y][x]);
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] box = new SudokuField[9];
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
                box[i] = new SudokuField(board[row][column]);
                i++;
            }
        }
        return new SudokuBox(box);
    }

}

