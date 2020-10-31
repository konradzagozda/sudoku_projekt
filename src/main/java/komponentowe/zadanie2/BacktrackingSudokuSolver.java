package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard board) {
        fillRecursively(board);
    }

    private boolean fillRecursively(SudokuBoard board) {
        // find next empty cell:
        int rowIndex = 0;
        int columnIndex = 0;
        for (int i = 0; i < 81; i++) {
            rowIndex = i / 9;
            columnIndex = i % 9;
            if (board.get(rowIndex, columnIndex) == 0) {
                // make it random:
                List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                Collections.shuffle(values);
                for (Integer value : values) {
                    // check that value has not been used in a row:
                    if (SudokuBoard.checkInRow(value, rowIndex, board)) {
                        // check that value has not been used in a column:
                        if (SudokuBoard.checkInColumn(value, columnIndex, board)) {
                            // check that value has not been used in a square:
                            if (SudokuBoard.checkInSquare(value, rowIndex, columnIndex, board)) {
                                // now we can try the number...
                                board.set(rowIndex, columnIndex, value);
                                // magic is happening here:
                                if (SudokuBoard.isFull(board)) {
                                    return true;
                                } else {
                                    if (fillRecursively(board)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        board.set(rowIndex, columnIndex, 0);
        return false;
    }
}

