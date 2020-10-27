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
                    if (checkInRow(value, rowIndex, board)) {
                        // check that value has not been used in a column:
                        if (checkInColumn(value, columnIndex, board)) {
                            // find out which square we are in:
                            int[][] square = new int[3][3];

                            if (rowIndex < 3) {
                                if (columnIndex < 3) {
                                    for (int j = 0; j < 3; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            square[j][k] = board.get(j, k);
                                        }
                                    }
                                } else if (columnIndex < 6) {
                                    for (int j = 0; j < 3; j++) {
                                        for (int k = 3; k < 6; k++) {
                                            square[j][k % 3] = board.get(j, k);
                                        }
                                    }
                                } else { // columnIndex [6..8]
                                    for (int j = 0; j < 3; j++) {
                                        for (int k = 6; k < 9; k++) {
                                            square[j][k % 3] = board.get(j, k);
                                        }
                                    }
                                }
                            } else if (rowIndex < 6) {
                                if (columnIndex < 3) {
                                    for (int j = 3; j < 6; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            square[j % 3][k] = board.get(j, k);
                                        }
                                    }
                                } else if (columnIndex < 6) {
                                    for (int j = 3; j < 6; j++) {
                                        for (int k = 3; k < 6; k++) {
                                            square[j % 3][k % 3] = board.get(j, k);
                                        }
                                    }
                                } else { // columnIndex [6..8]
                                    for (int j = 3; j < 6; j++) {
                                        for (int k = 6; k < 9; k++) {
                                            square[j % 3][k % 3] = board.get(j, k);
                                        }
                                    }
                                }
                            } else { // rowIndex [6..8]
                                if (columnIndex < 3) {
                                    for (int j = 6; j < 9; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            square[j % 3][k] = board.get(j, k);
                                        }
                                    }
                                } else if (columnIndex < 6) {
                                    for (int j = 6; j < 9; j++) {
                                        for (int k = 3; k < 6; k++) {
                                            square[j % 3][k % 3] = board.get(j, k);
                                        }
                                    }
                                } else { // columnIndex [6..8]
                                    for (int j = 6; j < 9; j++) {
                                        for (int k = 6; k < 9; k++) {
                                            square[j % 3][k % 3] = board.get(j, k);
                                        }
                                    }
                                }
                            }
                            // check that value has not been used in a square:
                            if ((IntStream.of(square[0]).noneMatch(x -> x == value)
                                    && IntStream.of(square[1]).noneMatch(x -> x == value)
                                    && IntStream.of(square[2]).noneMatch(x -> x == value))) {
                                // now we can try the number...
                                board.set(rowIndex, columnIndex, value);
                                // magic is happening here:
                                if (isFull(board)) {
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

    private boolean checkInRow(int value, int rowIndex, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (value == board.get(rowIndex, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkInColumn(int value, int columnIndex, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (value == board.get(i, columnIndex)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFull(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board.get(row, column) == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}

