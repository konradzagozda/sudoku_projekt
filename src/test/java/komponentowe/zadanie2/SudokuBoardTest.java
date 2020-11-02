package komponentowe.zadanie2;

import org.junit.jupiter.api.*;

import javax.security.auth.login.FailedLoginException;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    SudokuBoard testBoard;
    int[][] rows;

    @BeforeEach
    void setupBoard() throws WrongSudokuStateException {
        testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        rows = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                rows[row][column] = testBoard.get(row, column);
            }
        }
    }

    @Test
    void getTest() {
        // test all possible correct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int value = 0; value < 9; value++) {
                    testBoard.set(i,j,value);
                    assertEquals(value, testBoard.get(i, j));
                }
            }
        }

        // test incorrect
        int[] incorrectValues = new int[]{-10,-1,9,10};

        for (int x: incorrectValues
             ) {
            for (int y : incorrectValues
                 ) {
                assertThrows(IllegalArgumentException.class, () -> testBoard.get(x, y));
            }
        }

    }


    @Test
    void setTest() {
        for (int value = 0; value <= 9; value++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    testBoard.set(i,j, value);
                    assertEquals(value, testBoard.get(i,j));
                }
            }
        }

        int[] incorrectCoordinates = new int[]{-10,-1,9,10};
        int[] incorrectValues = new int[]{-10,-1,10,100};

        for (int x: incorrectCoordinates
             ) {
            for (int y: incorrectCoordinates
                 ) {
                for (int value: incorrectValues
                     ) {
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(x,y, value));
                }
            }
        }
    }




    @Test
    void solveGameTest() throws WrongSudokuStateException {
        // all fields are not 0s...
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard2.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(0, testBoard2.get(i, j));
            }
        }

        SudokuBoard testBoard3 = new SudokuBoard(board -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board.set(i,j,3); // fill in board with trash values
                }
            }
        });
        assertThrows(WrongSudokuStateException.class, testBoard3::solveGame);

    }

    @Test
    void fillBoardUniqueSudokuTest() throws WrongSudokuStateException {
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard2.solveGame();

        assertNotEquals(testBoard, testBoard2);
    }


    @Test
    void equalsTest() {
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertFalse(testBoard.equals(testBoard2));
        assertTrue(testBoard.equals(testBoard));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard.set(i,j,testBoard2.get(i,j));
            }
        }
        assertTrue(testBoard.equals(testBoard2));

        assertFalse(testBoard.equals(null));
        assertFalse(testBoard.equals(new Object()));
    }



}



