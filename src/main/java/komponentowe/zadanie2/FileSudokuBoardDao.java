package komponentowe.zadanie2;

public class FileSudokuBoardDao implements Dao, AutoCloseable {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void close() throws Exception {
        //todo
    }

    @Override
    public <T> T read() {
        return null;
    }

    @Override
    public <T> void write(T obj) {

    }

    @Override
    public void finalize() {

    };
}
