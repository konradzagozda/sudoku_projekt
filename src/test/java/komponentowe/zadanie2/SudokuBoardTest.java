package komponentowe.zadanie2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SudokuBoardTest {

    SudokuBoard newBoard;

    @BeforeEach
    void setupBoard() {
        newBoard = new SudokuBoard();
    }

    @Test
    void fillBoardTest() {
        newBoard.fillBoard();
        assertEquals(45, newBoard.getSudokuBoard()[1][1]);
    }
}
