package komponentowe.zadanie2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {

    @Override
    public void solve(SudokuBoard board) {
        fillRecursively(board);
    }

    private boolean fillRecursively(SudokuBoard board) {
        // find next empty cell:
        int y;
        int x;
        int i = 0;
        while (true) {
            y = i / 9;
            x = i % 9;
            i++;
            if (board.get(x, y) == 0) {
                // make it random:
                List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                Collections.shuffle(values);
                for (Integer value : values) {
                    // create row:
                    if (board.getRow(y).tryValue(value).verify()) {
                        // check that value has not been used in a column:
                        if (board.getColumn(x).tryValue(value).verify()) {
                            // check that value has not been used in a square:
                            if (board.getBox(x, y).tryValue(value).verify()) {
                                // now we can try the number...
                                board.set(x, y, value);
                                // magic is happening here:
                                if (board.isFull()) {
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
        board.set(x, y, 0);
        return false;
    }
}