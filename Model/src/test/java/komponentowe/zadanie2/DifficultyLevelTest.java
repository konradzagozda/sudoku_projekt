package komponentowe.zadanie2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void deleteFieldsTest() throws WrongSudokuStateException {
        SudokuField[][] fields = new SudokuField[9][9];

        DifficultyLevel easy = DifficultyLevel.EASY;
        DifficultyLevel medium = DifficultyLevel.MEDIUM;
        DifficultyLevel hard = DifficultyLevel.HARD;

        DifficultyLevel[] levels = {easy, medium, hard};

        for (var level: levels) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fields[i][j] = new SudokuField();
                }
            }
            SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
            board.solveGame();
            level.deleteFields(board);
            assertFalse(board.isFull());
        }



    }
}