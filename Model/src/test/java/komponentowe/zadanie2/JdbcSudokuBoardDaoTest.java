package komponentowe.zadanie2;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JdbcSudokuBoardDaoTest {

    @Test
    public void namesTest() throws SQLException {
        try(JdbcSudokuBoardDao jdbcDao = SudokuBoardDaoFactory.getJdbcSudokuBoardDao("testBoard3");) {
            var names = jdbcDao.getSudokuNames();
            System.out.println("names:");
            for (var name : names) {
                System.out.println(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    void daoTest() throws Exception {
        //1. save sudokuboard:
        try(JdbcSudokuBoardDao jdbcDao = SudokuBoardDaoFactory.getJdbcSudokuBoardDao("testBoard2");) {
            SudokuField[][] fields = new SudokuField[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fields[i][j] = new SudokuField();
                }
            }
            SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
            board.solveGame();
            SaveObject saveObject = new SaveObject(board);
            jdbcDao.write(saveObject);

            //2. read sudokuboard

            SaveObject saveObject1 = jdbcDao.read();
            assertEquals(saveObject.getCurrent(), saveObject1.getCurrent());


            //3. update board and read again, assure it's different than original one.

            SudokuBoard boardRead = saveObject1.getCurrent();

            boardRead.set(2, 2, 5);
            boardRead.set(2, 3, 5);
            boardRead.set(3, 3, 5);

            jdbcDao.update(saveObject1);
            saveObject1 = jdbcDao.read();

            boardRead = saveObject1.getCurrent();
            SudokuBoard originalBoard = saveObject1.getOriginal();
            assertNotEquals(board, boardRead);
            assertEquals(originalBoard, board);

            //3. delete board

            jdbcDao.delete();

            //4. delete again and check if it throws something

            assertThrows(SudokuSQLException.class, jdbcDao::delete);
        }

    }
}