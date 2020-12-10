package komponentowe.zadanie2;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileSudokuBoardDao<T> implements Dao<T> {

    private final String fileName;
    private final FileInputStream fileIn;
    private final FileOutputStream fileOut;

    public FileSudokuBoardDao(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.fileOut = new FileOutputStream(fileName);
        this.fileIn = new FileInputStream(fileName);
    }


    // automatically called with try with resources
    @Override
    public void close() throws Exception {
        fileIn.close();
        fileOut.close();
    }

    @Override
    public T read() throws ClassNotFoundException, IOException {
        try (ObjectInputStream objOut = new ObjectInputStream(fileIn)) {
            T object = (T) objOut.readObject();
            return object;
        }
    }

    @Override
    public void write(T obj) throws IOException {
        try (ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(obj);
        }
    }

}