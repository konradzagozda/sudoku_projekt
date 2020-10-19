package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuBoardTest {

    SudokuBoard testBoard;

    @BeforeEach
    void setupBoard() {
        testBoard = new SudokuBoard();
    }

    @Test
    void fillBoardTest() {
        testBoard.fillBoard();
        testBoard.showBoard();
        System.out.println(testBoard.getSudokuBoard()[0][8]);
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                int rowSum =+ testBoard.getSudokuBoard()[i][j];
                assertEquals(45, rowSum);
            }
        }
    }
}
