package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    SudokuBoard board;

    @BeforeEach
    void setup() throws FailedLoginException {
        board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
    }


    @Test
    void getFieldValueTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(0, board.get(i,j));
            }
        }
    }

    @Test
    void equalsFieldTest() {
        SudokuField field = new SudokuField(5);
        SudokuField field2 = new SudokuField(6);

        assertNotEquals(field, field2);
        assertEquals(field, field);

        field.setFieldValue(field2.getFieldValue());
        assertEquals(field, field2);

        assertNotEquals(null, field);
        assertNotEquals(field, new Object());
    }





    @Test
    void equalsTest() {
    }
}