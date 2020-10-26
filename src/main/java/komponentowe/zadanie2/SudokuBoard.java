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

    public void fillBoard() {
        fillRecursively(board);
    }

    private boolean fillRecursively(int[][] board) {
        // find next empty cell:
        int rowIndex = 0;
        int columnIndex = 0;
        for (int i = 0; i < 81; i++) {
            rowIndex = i / 9;
            columnIndex = i % 9;
            if (board[rowIndex][columnIndex] == 0) {
                // make it random:
                List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                Collections.shuffle(values);
                for (Integer value : values) {
                    // check that value has not been used in a row:
                    if (IntStream.of(board[rowIndex]).noneMatch(x -> x == value)) {
                        int[] column = new int[]{
                                board[0][columnIndex],
                                board[1][columnIndex],
                                board[2][columnIndex],
                                board[3][columnIndex],
                                board[4][columnIndex],
                                board[5][columnIndex],
                                board[6][columnIndex],
                                board[7][columnIndex],
                                board[8][columnIndex]
                        };
                        // check that value has not been used in a column:
                        if (IntStream.of(column).noneMatch(x -> x == value)) {
                            // find out which square we are in:
                            int[][] square = new int[3][3];
                            if (rowIndex < 3) {
                                if (columnIndex < 3) {
                                    for (int j = 0; j < 3; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            square[j][k] = get(j, k);
                                        }
                                    }
                                } else if (columnIndex < 6) {
                                    for (int j = 0; j < 3; j++) {
                                        for (int k = 3; k < 6; k++) {
                                            square[j][k % 3] = get(j, k);
                                        }
                                    }
                                } else { // columnIndex [6..8]
                                    for (int j = 0; j < 3; j++) {
                                        for (int k = 6; k < 9; k++) {
                                            square[j][k % 3] = get(j, k);
                                        }
                                    }
                                }
                            } else if (rowIndex < 6) {
                                if (columnIndex < 3) {
                                    for (int j = 3; j < 6; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            square[j % 3][k] = get(j, k);
                                        }
                                    }
                                } else if (columnIndex < 6) {
                                    for (int j = 3; j < 6; j++) {
                                        for (int k = 3; k < 6; k++) {
                                            square[j % 3][k % 3] = get(j, k);
                                        }
                                    }
                                } else { // columnIndex [6..8]
                                    for (int j = 3; j < 6; j++) {
                                        for (int k = 6; k < 9; k++) {
                                            square[j % 3][k % 3] = get(j, k);
                                        }
                                    }
                                }
                            } else { // rowIndex [6..8]
                                if (columnIndex < 3) {
                                    for (int j = 6; j < 9; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            square[j % 3][k] = get(j, k);
                                        }
                                    }
                                } else if (columnIndex < 6) {
                                    for (int j = 6; j < 9; j++) {
                                        for (int k = 3; k < 6; k++) {
                                            square[j % 3][k % 3] = get(j, k);
                                        }
                                    }
                                } else { // columnIndex [6..8]
                                    for (int j = 6; j < 9; j++) {
                                        for (int k = 6; k < 9; k++) {
                                            square[j % 3][k % 3] = get(j, k);
                                        }
                                    }
                                }
                            }
                            // check that value has not been used in a square:
                            if ((IntStream.of(square[0]).noneMatch(x -> x == value)
                                    && IntStream.of(square[1]).noneMatch(x -> x == value)
                                    && IntStream.of(square[2]).noneMatch(x -> x == value))) {
                                // now we can try the number...
                                setField(rowIndex, columnIndex, value);
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
        setField(rowIndex, columnIndex, 0);
        return false;
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

    public void setField(int x, int y, int value) {
        board[x][y] = value;
    }

    public int get(int rowIndex, int columnIndex) {
        if (columnIndex < 9 && rowIndex < 9) {
            return board[rowIndex][columnIndex];
        } else {
            return 0;
        }
    }

    private boolean isFull(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int[] row : board
        ) {
            System.out.println(Arrays.toString(row));
        }
    }
}
