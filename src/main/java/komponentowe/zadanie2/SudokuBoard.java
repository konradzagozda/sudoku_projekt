package komponentowe.zadanie2;

import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    private int[][] sudokuBoard;

    public SudokuBoard() {
        sudokuBoard = new int[9][9];
    }

    public void fillBoard() {
        //todo: backtracking method

        Random rand = new Random();
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                sudokuBoard[i][j] = rand.nextInt(9) + 1;
            }
        }
    }

    public void showBoard() {
        System.out.println(Arrays.deepToString(sudokuBoard));
    }

    //getters

    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

}
