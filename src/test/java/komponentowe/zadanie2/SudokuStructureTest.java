package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuStructureTest {

    SudokuField[] fields;
    SudokuStructure testRow;
    SudokuStructure testRow2;

    @BeforeEach
    void setup() {
        fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }

        testRow = new SudokuRow(fields);
        testRow2 = new SudokuRow(fields);
    }


    @Test
    void tryValueTest() {
        testRow.tryValue(1);
        assertTrue(testRow.verify());
        testRow.tryValue(1);
        assertFalse(testRow.verify());
    }


    @Test
    void equalsTest() {
        assertTrue(testRow.equals(testRow));
        assertTrue(testRow.equals(testRow2));
        assertFalse(testRow.equals(null));
    }

    @Test
    void hashCodeTest() {
        assertTrue(testRow.hashCode() != 0);
    }

    @Test
    void toStringTest(){
        System.out.println(testRow.toString());
        assertTrue(testRow.toString().length() != 0);
    }
}