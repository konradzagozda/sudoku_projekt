package komponentowe.zadanie2;

public class SudokuBoardDaoFactory {

    Dao getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
