package komponentowe.zadanie2;

import java.io.FileNotFoundException;

public class SudokuBoardDaoFactory {

    public static Dao getFileDao(String fileName) throws FileNotFoundException {
        return new FileSudokuBoardDao(fileName);
    }


}
