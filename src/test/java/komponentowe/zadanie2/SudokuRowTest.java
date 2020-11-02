package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {

    SudokuField[] fields;
    SudokuRow testRow;

    @BeforeEach
    void setup() {
        fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }

        testRow = new SudokuRow(fields);
    }


    @Test
    void tryValueTest() {
        testRow.tryValue(1);
        assertTrue(testRow.verify());
        testRow.tryValue(1);
        assertFalse(testRow.verify());
    }
}