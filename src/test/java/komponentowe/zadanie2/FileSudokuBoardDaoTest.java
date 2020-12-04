package komponentowe.zadanie2;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {



    @Test
    void readWriteTest() {

        // get DAO
        try (FileSudokuBoardDao dao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("siema.txt")) {


            // create SudokuBoard and Container for writing
            SudokuField[][] fields = new SudokuField[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fields[i][j] = new SudokuField();
                }
            }
            SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
            board.solveGame();

            SudokuContainer sudokuContainer = new SudokuContainer(board);

            // write Container with contents
            dao.write(sudokuContainer);

            // read Container
            SudokuContainer sudokuContainer2 = dao.read();

            // tests
            assertEquals(sudokuContainer, sudokuContainer2);
            assertNotEquals(sudokuContainer, new SudokuContainer(
                    new SudokuBoard(new SudokuField[9][9], new BacktrackingSudokuSolver())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    void testFinalize() throws Exception {
        FileSudokuBoardDao dao;
        try {
            dao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("siema.txt");
            dao.finalize();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}