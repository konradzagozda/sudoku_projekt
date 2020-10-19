package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    private int[][] sudokuBoard;

    public SudokuBoard() {
        sudokuBoard = new int[9][9];
    }

    public void fillBoard() {
        System.out.println("Backtracking");

        Random rand = new Random();
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                sudokuBoard[i][j] = (int) rand.nextInt(9) + 1;
            }
        }
    }

    public void showBoard() {
        System.out.println("Showing board...");
        System.out.println(Arrays.deepToString(sudokuBoard));
    }

    //getters

    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

}
