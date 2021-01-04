package komponentowe.zadanie2;

import java.io.File;
import java.io.FileNotFoundException;

public class SudokuBoardDaoFactory {

    public static Dao getFileDao(File fileName) throws FileNotFoundException {
        return new FileSudokuBoardDao(fileName);
    }


}
