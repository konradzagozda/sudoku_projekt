package komponentowe.zadanie2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {

    @Test
    void readTest() throws SQLException, IOException, ClassNotFoundException {
        JdbcSudokuBoardDao jdbcDao = SudokuBoardDaoFactory.getJdbcSudokuBoardDao("sudokunr1");
        jdbcDao.read();
    }

    @Test
    void writeTest() {
    }
}