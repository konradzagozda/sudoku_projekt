package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SudokuBoard {

    private final int[][] board;

    public SudokuBoard() {
        board = new int[9][9];
    }

    public void solveGame() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(this);
    }

    public void set(int x, int y, int value) {
        board[x][y] = value;
    }

    public int get(int rowIndex, int columnIndex) {
        if (columnIndex < 9 && rowIndex < 9) {
            return board[rowIndex][columnIndex];
        } else {
            return 0;
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

    public void printBoard() {
        for (int[] row : board
        ) {
            System.out.println(Arrays.toString(row));
        }
    }
}
