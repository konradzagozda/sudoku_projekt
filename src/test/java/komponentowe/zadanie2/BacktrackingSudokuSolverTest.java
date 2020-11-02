package komponentowe.zadanie2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    SudokuBoard sudokuBoard;
    SudokuField[][] sudokuFields;


    @BeforeEach
    void createSudokuBoard() {
        sudokuFields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuFields[i][j] = new SudokuField();
            }
        }
        sudokuBoard = new SudokuBoard(sudokuFields, new BacktrackingSudokuSolver());
    }

    @Test
    void solveTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(sudokuBoard);

        int[][] rows = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                rows[row][column] = sudokuBoard.get(row, column);
            }
        }

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
        }

        sum = 0;
        for (int i = 0; i < 3; i++) {

            // second block
            for (int j = 3; j < 6; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        sum = 0;
        for (int i = 0; i < 3; i++) {

            // third block
            for (int j = 6; j < 9; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        // rows at indices 3,4,5
        sum = 0;
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        sum = 0;
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        sum = 0;
        for (int i = 3; i < 6; i++) {
            for (int j = 6; j < 9; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        // rows at indices 6,7,8
        sum = 0;
        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        sum = 0;
        for (int i = 6; i < 9; i++) {
            for (int j = 3; j < 6; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);
        sum = 0;
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                sum += rows[i][j];
            }
        }
        assertEquals(45, sum);

    }
}