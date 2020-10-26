package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SudokuBoard {

    private final int[][] sudokuBoard;

    public SudokuBoard() {
        sudokuBoard = new int[9][9];
    }

    public void fillBoard() {
        fillRecursively(sudokuBoard);
    }

    private boolean fillRecursively(int[][] sudokuBoard) {
        // find next empty cell:
        int rowIndex = 0;
        int columnIndex = 0;
        for (int i = 0; i < 81; i++) {
            rowIndex = i / 9;
            columnIndex = i % 9;
            if (sudokuBoard[rowIndex][columnIndex] == 0) {
                // make it random:
                List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                Collections.shuffle(values);
                for (Integer value : values) {
                    // check that value has not been used in a row:
                    if (IntStream.of(sudokuBoard[rowIndex]).noneMatch(x -> x == value)) {
                        int[] column = new int[]{
                                sudokuBoard[0][columnIndex],
                                sudokuBoard[1][columnIndex],
                                sudokuBoard[2][columnIndex],
                                sudokuBoard[3][columnIndex],
                                sudokuBoard[4][columnIndex],
                                sudokuBoard[5][columnIndex],
                                sudokuBoard[6][columnIndex],
                                sudokuBoard[7][columnIndex],
                                sudokuBoard[8][columnIndex]
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
                                if (isFull(sudokuBoard)) {
                                    return true;
                                } else {
                                    if (fillRecursively(sudokuBoard)) {
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
        return Arrays.deepEquals(sudokuBoard, that.sudokuBoard);
    }

    public void setField(int x, int y, int value) {
        sudokuBoard[x][y] = value;
    }

    public int get(int rowIndex, int columnIndex) {
        if (columnIndex < 9 && rowIndex < 9) {
            return sudokuBoard[rowIndex][columnIndex];
        } else {
            return 0;
        }
    }

    private boolean isFull(int[][] sudokuBoard) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (sudokuBoard[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int[] row : sudokuBoard) {
            System.out.println(Arrays.toString(row));
        }
    }
}
