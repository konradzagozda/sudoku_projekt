package komponentowe.zadanie2;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuBoardTest {

    SudokuBoard testBoard;
    int[][] rows;

    @BeforeEach
    void setupBoard() {
        testBoard = new SudokuBoard();
        testBoard.fillBoard();
        rows = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                rows[row][column] = testBoard.get(row,column);
            }
        }
    }

    @Test
    void fillBoardTest() {
        // test if rows have correct sum:
        for (int i = 0; i < 9; i++) {
            assertEquals(45, Arrays.stream(rows[i]).sum());
        }

        // test if columns have correct sum:
        int[] sums = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sums[j] += rows[i][j];
            }
        }
        int[] checkSums = new int[9];
        Arrays.fill(checkSums, 45);
        assertArrayEquals(checkSums, sums);


        // test if blocks have correct sum:
        int sum = 0;
        // rows at indices 0,1,2
        for (int i = 0; i < 3; i++) {
            // first block
            for (int j = 0; j < 3; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
            // second block
            for (int j = 3; j < 6; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
            // third block
            for (int j = 6; j < 9; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
        }
        // rows at indices 3,4,5
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
            for (int j = 3; j < 6; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
            for (int j = 6; j < 9; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
        }

        // rows at indices 6,7,8
        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
            for (int j = 3; j < 6; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
            for (int j = 6; j < 9; j++) {
                sum += rows[i][j];
            }
            assertEquals(45, sum);
            sum = 0;
        }
    }
}


