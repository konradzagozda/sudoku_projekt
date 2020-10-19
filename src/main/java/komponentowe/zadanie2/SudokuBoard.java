package komponentowe.zadanie2;

public class SudokuBoard {

    private int[][] sudokuBoard;

    public SudokuBoard() {
        sudokuBoard = new int[9][9];
    }

    public void fillBoard() {
        System.out.println("Backtracking");
    }

    public void showBoard() {
        System.out.println("Showing board...");
    }

    //getters


    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }
}
