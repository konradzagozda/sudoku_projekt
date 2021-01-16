package komponentowe.zadanie2;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileSudokuBoardDao<T> implements Dao<T> {

    private final File file;

    public FileSudokuBoardDao(File file) {
        this.file = file;
    }


    // automatically called with try with resources
    @Override
    public void close() {

    }

    @Override
    public T read() throws ClassNotFoundException, IOException {
        try (var fileIn = new FileInputStream(file);
             var objOut = new ObjectInputStream(fileIn)) {
            T object = (T) objOut.readObject();
            return object;
        }
    }

    @Override
    public void write(T obj) throws Exception {
        try (var fileOut = new FileOutputStream(file);
             var objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(obj);
        }
    }

}
