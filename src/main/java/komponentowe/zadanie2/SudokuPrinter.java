package komponentowe.zadanie2;

import java.io.IOException;

public class SudokuPrinter implements SudokuObserver {
    private SudokuField[][] board;

    public SudokuPrinter(SudokuField[][] board) {
    }

    public void printSudoku() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                System.out.print(board[y][x].getFieldValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void update(SudokuField[][] board) {
        this.board = board;
        printSudoku();
    }
}
