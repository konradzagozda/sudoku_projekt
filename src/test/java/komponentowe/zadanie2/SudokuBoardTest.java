package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    final int[] INCORRECT_VALUES = new int[]{-10, -1, 10};
    final int[] CORRECT_VALUES = new int[]{0, 1, 2, 3, 9};
    final int[] INCORRECT_COORDINATES = new int[]{-5, 10, 15};
    final int[] CORRECT_COORDINATES = new int[]{0, 3, 8};
    SudokuBoard testBoard;
    SudokuField[][] sudokuFields;
    SudokuField[][] sudokuFields2;
    int[][] rows;

    @BeforeEach
    void setupBoard() throws WrongSudokuStateException {
        sudokuFields = new SudokuField[9][9];
        sudokuFields2 = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuFields[i][j] = new SudokuField();
                sudokuFields2[i][j] = new SudokuField();
            }
        }


        testBoard = new SudokuBoard(sudokuFields, new BacktrackingSudokuSolver());
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

        // test correct
        for (int x : CORRECT_COORDINATES
        ) {
            for (int y : CORRECT_COORDINATES
            ) {
                testBoard.set(x, y, 5);
                assertEquals(5, testBoard.get(x, y));
            }
        }
        // test incorrect
        for (int x : INCORRECT_VALUES
        ) {
            for (int y : INCORRECT_VALUES
            ) {
                assertThrows(IllegalArgumentException.class, () -> testBoard.get(x, y));
            }
        }
        // mix of incorrect and correct
        for (int x : CORRECT_VALUES
        ) {
            for (int y : INCORRECT_VALUES
            ) {
                assertThrows(IllegalArgumentException.class, () -> testBoard.get(x, y));
                assertThrows(IllegalArgumentException.class, () -> testBoard.get(y, x));
            }
        }

    }


    @Test
    void setTest() {
        for (int x : CORRECT_COORDINATES
        ) {
            for (int y : CORRECT_COORDINATES
            ) {
                for (int value : CORRECT_VALUES
                ) {
                    testBoard.set(x, y, value);
                    assertEquals(value, testBoard.get(x, y));
                }
            }
        }

        for (int x : INCORRECT_COORDINATES
        ) {
            for (int y : INCORRECT_COORDINATES
            ) {
                for (int value : INCORRECT_VALUES
                ) {
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(x, y, value));
                }
            }
        }

        for (int x : CORRECT_COORDINATES
        ) {
            for (int y : CORRECT_COORDINATES
            ) {
                for (int value : INCORRECT_VALUES
                ) {
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(x, y, value));
                }
            }
        }

        for (int x : INCORRECT_COORDINATES
        ) {
            for (int y : INCORRECT_COORDINATES
            ) {
                for (int value : CORRECT_VALUES
                ) {
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(x, y, value));
                }
            }
        }

        for (int x : CORRECT_COORDINATES
        ) {
            for (int y : INCORRECT_COORDINATES
            ) {
                for (int value : CORRECT_VALUES
                ) {
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(x, y, value));
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(y, x, value));

                }
            }
        }

        for (int x : CORRECT_COORDINATES
        ) {
            for (int y : INCORRECT_COORDINATES
            ) {
                for (int value : INCORRECT_VALUES
                ) {
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(x, y, value));
                    assertThrows(IllegalArgumentException.class, () -> testBoard.set(y, x, value));

                }
            }
        }


    }


    @Test
    void solveGameTest() throws WrongSudokuStateException {
        // all fields are not 0s...
        SudokuBoard testBoard2 = new SudokuBoard(sudokuFields2, new BacktrackingSudokuSolver());
        testBoard2.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(0, testBoard2.get(i, j));
            }
        }

        SudokuBoard testBoard3 = new SudokuBoard(sudokuFields2, board -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board.set(i, j, 3); // fill in board with trash values
                }
            }
        });
        assertThrows(WrongSudokuStateException.class, testBoard3::solveGame);

        SudokuBoard testBoard4 = new SudokuBoard(sudokuFields2, board -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board.set(i, j, j + 1);
                }
            }
        });
        assertThrows(WrongSudokuStateException.class, testBoard4::solveGame);

        SudokuBoard testBoard5 = new SudokuBoard(sudokuFields2, board -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board.set(i, j, i + 1);
                }
            }
        });
        assertThrows(WrongSudokuStateException.class, testBoard5::solveGame);

        int[][] wrongSquaresBoard = new int[][]{
                {7, 3, 9, 8, 2, 1, 4, 6, 5},
                {2, 7, 4, 9, 5, 3, 8, 1, 6},
                {4, 9, 3, 1, 6, 8, 7, 5, 2},
                {8, 2, 5, 3, 4, 6, 9, 7, 1},
                {6, 1, 2, 7, 8, 9, 5, 3, 4},
                {3, 8, 1, 5, 9, 4, 6, 2, 7},
                {5, 4, 8, 6, 1, 7, 2, 9, 3},
                {9, 6, 7, 2, 3, 5, 1, 4, 8},
                {1, 5, 6, 4, 7, 2, 3, 8, 9}
        };

        SudokuBoard testBoard6 = new SudokuBoard(sudokuFields2, board -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board.set(i, j, wrongSquaresBoard[i][j]);
                }
            }
        });
        assertThrows(WrongSudokuStateException.class, testBoard6::solveGame);


    }

    @Test
    void fillBoardUniqueSudokuTest() throws WrongSudokuStateException {
        SudokuBoard testBoard2 = new SudokuBoard(sudokuFields2, new BacktrackingSudokuSolver());
        testBoard2.solveGame();

        assertNotEquals(testBoard, testBoard2);
    }


    @Test
    void equalsTest() {
        SudokuBoard testBoard2 = new SudokuBoard(sudokuFields2, new BacktrackingSudokuSolver());
        assertFalse(testBoard.equals(testBoard2));
        assertTrue(testBoard.equals(testBoard));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard.set(i, j, testBoard2.get(i, j));
            }
        }
        assertTrue(testBoard.equals(testBoard2));

        assertFalse(testBoard.equals(null));
        assertFalse(testBoard.equals(new Object()));
    }

    @Test
    void observersTest() {
        String test = "empty";

        var observer = new SudokuObserver() {
            @Override
            public void update(SudokuField[][] board) {
                assertTrue(true);
            }
        };

        assertEquals(0, testBoard.getSudokuObservers().size());
        testBoard.register(observer);
        testBoard.register(observer);
        testBoard.register(observer);
        assertEquals(1 , testBoard.getSudokuObservers().size());
        testBoard.notifyObservers();
        testBoard.unregister(observer);
        assertEquals(0, testBoard.getSudokuObservers().size());
    }


    @Test
    void hashCodeTest() {
        assertTrue(testBoard.hashCode() != 0);
        assertTrue(testBoard.hashCode() == testBoard.hashCode());
    }

    @Test
    void toStringTest() {
        System.out.println(testBoard.toString());
        assertTrue(0 != testBoard.toString().length());
    }

}



