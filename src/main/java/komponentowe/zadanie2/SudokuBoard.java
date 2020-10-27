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
