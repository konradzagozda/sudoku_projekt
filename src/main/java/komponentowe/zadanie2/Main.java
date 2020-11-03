package komponentowe.zadanie2;

public class Main {

    public static void main(String... args) {
        SudokuField[][] fields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new SudokuField();
            }
        }

        SudokuBoard sudokuBoard = new SudokuBoard(fields, new BacktrackingSudokuSolver());
        SudokuPrinter printer = new SudokuPrinter(fields);
        sudokuBoard.register(printer);
        try {
            sudokuBoard.solveGame();
        } catch (WrongSudokuStateException e) {
            e.printStackTrace();
            System.out.println("Solving sudoku failed...");
        }
    }


}
