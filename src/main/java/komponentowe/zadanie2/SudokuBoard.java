package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SudokuBoard {

    private final int[][] board;
    SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        board = new int[9][9];
        this.solver = solver;
    }

    static boolean isFull(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board.get(row, column) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkInRow(int value, int rowIndex, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (value == board.get(rowIndex, i)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkInColumn(int value, int columnIndex, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (value == board.get(i, columnIndex)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkInSquare(int value, int rowIndex, int columnIndex, SudokuBoard board) {
        int[] square = new int[9];
        // 0 1 2 => 0   x / 3 = 0
        // 3 4 5 => 1   x / 3 = 1
        // 6 7 8 => 2   x / 3 = 2
        // now we know the square...
        // find out numbers to iterate...
        // for row or column:
        // if 0 => 0 1 2  x * 3 + 0 , x * 3 + 1, x * 3 + 2
        // if 1 => 3 4 5  x * 3 + 0 , x * 3 + 1, x * 3 + 2
        // if 2 => 6 7 8  x * 3 + 0 , x * 3 + 1, x * 3 + 2
        int squareRow = rowIndex / 3;
        int squareColumn = columnIndex / 3;
        int[] rowsToIterate = new int[3];
        int[] columnsToIterate = new int[3];
        for (int i = 0; i < 3; i++) {
            rowsToIterate[i] = squareRow * 3 + i;
            columnsToIterate[i] = squareColumn * 3 + i;
        }
        // fill in square...
        int i = 0;
        for (int row : rowsToIterate
        ) {
            for (int column : columnsToIterate
            ) {
                square[i] = board.get(row, column);
                i++;
            }
        }
        // check if value is not in square...
        for (int elem : square
        ) {
            if (value == elem) {
                return false;
            }
        }
        return true;

    }

    public void solveGame() {
        solver.solve(this);
    }

    public void set(int x, int y, int value) {
        if (value <= 9 && value >= 0) {
            board[x][y] = value;
        }
    }

    public int get(int rowIndex, int columnIndex) {
        if (columnIndex < 9 && rowIndex < 9 && columnIndex >= 0 && rowIndex >= 0) {
            return board[rowIndex][columnIndex];
        } else {
            return -1;
        }
    }

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
}
