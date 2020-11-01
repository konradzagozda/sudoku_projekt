package komponentowe.zadanie2;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    SudokuBoard testBoard;
    int[][] rows;

    @BeforeEach
    void setupBoard() {
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
        // test limit values...
        testBoard.set(0, 0, 9);
        assertEquals(9, testBoard.get(0, 0));
        testBoard.set(8, 8, 3);
        assertEquals(3, testBoard.get(8, 8));

        // test middle...
        testBoard.set(5, 5, 5);
        assertEquals(5, testBoard.get(5, 5));

        // test incorrect... (should be -1)
        assertEquals(-1, testBoard.get(10, 10));
        assertEquals(-1, testBoard.get(-1, -1));
    }


    @Test
    void setTest() {
        // we can test putting in wrong values, but not wrong coordinates
        testBoard.set(5, 5, 8);
        assertEquals(8, testBoard.get(5, 5));
        testBoard.set(5, 5, 10); // won't work because 10 is not in 0..9.
        assertEquals(8, testBoard.get(5, 5));
        testBoard.set(5, 5, 0); // 0 is fine
        assertEquals(0, testBoard.get(5, 5));
        testBoard.set(5, 5, -1); // -1 won't work
        assertEquals(0, testBoard.get(5, 5));

    }

    @Test
    void solveGameTest() {
        for (int[] row : rows
        ) {
            for (int elem : row
            ) {
                assertNotEquals(0, elem);
                // if here's no 0's then solver has been called,
                // and method's responsibility is to call solver.
                // failing this test means solver failed.
            }
        }
    }

    @Test
    void fillBoardUniqueSudokuTest() {
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard2.solveGame();

        assertNotEquals(testBoard, testBoard2);
    }


    // assuming testBoard has been filled out correctly checks should return true for 0 and false for 1..9
//    @Test
//    void checkInColumnTest() {
//        for (int i = 0; i < 9; i++) {
//            assertTrue(SudokuBoard.checkInColumn(0, i, testBoard));
//        }
//        for (int i = 1; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//                assertFalse(SudokuBoard.checkInColumn(i, j, testBoard));
//            }
//        }
//    }
//
//    @Test
//    void checkInRowTest() {
//        for (int i = 0; i < 9; i++) {
//            assertTrue(SudokuBoard.checkInRow(0, i, testBoard));
//        }
//        for (int i = 1; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//                assertFalse(SudokuBoard.checkInRow(i, j, testBoard));
//            }
//        }
//    }
//
//    @Test
//    void checkInSquareTest() {
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                assertTrue(SudokuBoard.checkInSquare(0, j, i, testBoard));
//            }
//        }
//        for (int i = 1; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//                for (int k = 0; k < 9; k++) {
//                    assertFalse(SudokuBoard.checkInSquare(i, k, j, testBoard));
//                }
//            }
//        }
//    }

//    @RepeatedTest(5)
//    void printTest() {
//        testBoard.printBoard();
//    }
}



