package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    SudokuField sudokuField, sudokuField2;

    @BeforeEach
    void setup() {
        sudokuField = new SudokuField();
        sudokuField2 = new SudokuField();
    }


    @Test
    void getFieldValueTest() {
        for (int i = 0; i < 9; i++) {
            sudokuField.setFieldValue(i);
            assertEquals(i, sudokuField.getFieldValue());
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
    void hashCodeTest() {
        SudokuField sudokuField = new SudokuField();
        SudokuField sudokuField2 = new SudokuField();
        assertEquals(sudokuField.hashCode(),sudokuField2.hashCode());
    }


    @Test
    void equalsTest() {
        SudokuField sudokuField = new SudokuField();
        SudokuField sudokuField2 = new SudokuField(2);
        assertTrue(sudokuField.equals(sudokuField));
        assertFalse(sudokuField.equals(sudokuField2));
        sudokuField.setFieldValue(2);
        assertTrue(sudokuField.equals(sudokuField2));
        assertFalse(sudokuField.equals(null));
        assertFalse(sudokuField.equals(new Object()));
    }

    @Test
    void setFieldValueTest(){
        for (int i = 0; i < 9; i++) {
            sudokuField.setFieldValue(i);
            assertEquals(i, sudokuField.getFieldValue());
        }

        assertThrows(IllegalArgumentException.class,() -> sudokuField.setFieldValue(-5));
        assertThrows(IllegalArgumentException.class,() -> sudokuField.setFieldValue(15));
    }
}