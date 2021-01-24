package komponentowe.zadanie2;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SudokuBoardDaoFactory {

    public static Dao getFileDao(File fileName) throws FileNotFoundException {
        return new FileSudokuBoardDao(fileName);
    }

    public static JdbcSudokuBoardDao getJdbcSudokuBoardDao(String sudokuName) throws SQLException {
        return new JdbcSudokuBoardDao(sudokuName);
    }


}
