package komponentowe.zadanie2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FileSudokuBoardDaoTest {

    private static final Logger logger = LogManager.getLogger(FileSudokuBoardDaoTest.class);

    @Test
    void readWriteTest() {

        // get DAO
        try (FileSudokuBoardDao<SudokuBoard> dao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao(new File("siema.txt"))) {


            // create SudokuBoard and Container for writing
            SudokuField[][] fields = new SudokuField[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fields[i][j] = new SudokuField();
                }
            }
            SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
            board.solveGame();

            // write Container with contents
            dao.write(board);

            // read Container
            SudokuBoard board2 = dao.read();

            // tests
            assertEquals(board, board2);
            assertNotEquals(board,
                    new SudokuBoard(new SudokuField[9][9], new BacktrackingSudokuSolver()));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
        }

    }

}