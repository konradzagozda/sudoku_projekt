package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

public class SudokuBoard {

    private int[][] sudokuBoard;

    public SudokuBoard() {
        sudokuBoard = new int[9][9];
    }

    public void fillBoard() {
        //todo
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
