package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuContainerTest {

    SudokuContainer sudokuContainer;

    @BeforeEach
    void setUp() {
        SudokuField[][] fields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new SudokuField();
            }
        }
        SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
        sudokuContainer = new SudokuContainer(board);
    }

    @Test
    void testToString() {
        assertNotNull(sudokuContainer.toString());
    }

    @Test
    void testEquals() {
        assertTrue(sudokuContainer.equals(sudokuContainer));
        assertFalse(sudokuContainer.equals(new Object()));
    }

    @Test
    void testHashCode() {
        System.out.println(sudokuContainer.hashCode());
    }
}