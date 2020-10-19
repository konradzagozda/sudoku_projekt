package komponentowe.zadanie2;

import java.util.*;
import java.util.stream.IntStream;

public class SudokuBoard {

    private int[][] sudokuBoard;

    public SudokuBoard() {
        sudokuBoard = new int[9][9];
    }

    public void fillBoard(int[][] sudokuBoard) {
        // find next empty cell:
        for (int i = 0; i < 81; i++) {
            int rowIndex = i / 9;
            int columnIndex = i % 9;
            if (sudokuBoard[rowIndex][columnIndex] == 0) {
                // make it random:
                List<Integer> values = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
                Collections.shuffle(values);
                for (Integer value : values) {
                    // check that value has not been used in a row:
                    if ( IntStream.of(sudokuBoard[rowIndex]).anyMatch(x -> x == value)) {
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
                        if ( IntStream.of(column).anyMatch(x -> x == value)) {
                            // find out which square we are in:
                            int[] square = new int[9];
                            // todo:
                        }
                    }
                }
            }
        }
    }

    public int get(int rowIndex, int columnIndex) { //
        if (columnIndex < 9 && rowIndex < 9) {
            return sudokuBoard[rowIndex][columnIndex];
        } else {
            return 0;
        }
    }

    private boolean isFull(int[][] sudokuBoard) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (sudokuBoard[row][column] == 0) return false;
            }
        }
        return true;
    }

}
