package komponentowe.zadanie2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CloneTests {

    private static final Logger logger = LogManager.getLogger(CloneTests.class);


    @Test
    void cloneSudokuFieldTest() throws CloneNotSupportedException {
        SudokuField field1 = new SudokuField(2);
        var field2 = field1.clone();
        assertEquals(field1.compareTo(field2), 0);
        field1.setFieldValue(5);
        assertNotEquals(field1.compareTo(field2), 0);
        assertTrue(field1.compareTo(field2) > 0);
        assertTrue(field2.compareTo(field1) < 0);


        assertThrows(NullPointerException.class, () -> {
            field1.compareTo(null);
        });
    }

    @Test
    void cloneSudokuStructureTest() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            fields.add(new SudokuField(i+1)); // 1 2 3 .. 9
        }
        var row1 = new SudokuRow(fields);
        var row2 = row1.clone();
        assertSame(row1.fields, row2.fields); // its fine to have duplicates here
    }

    @Test
    void cloneSudokuBoardTest() throws WrongSudokuStateException, CloneNotSupportedException {
        SudokuField[][] fields = new SudokuField[9][9];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                fields[i][j] = new SudokuField();
            }
        }

        SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
        SudokuBoard board2 = board.clone();
        board.solveGame();
        logger.debug("board1: \n" + board.toString());
        logger.debug("board2: \n" + board2.toString());
        board2.solveGame();
        assertNotSame(board, board2);
        assertNotSame(board.getColumn(1), board2.getColumn(1));
    }
}
