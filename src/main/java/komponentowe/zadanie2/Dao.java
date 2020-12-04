package komponentowe.zadanie2;

import java.io.IOException;

public interface Dao {

    <T> T read() throws IOException, ClassNotFoundException;

    <T> void write(T obj) throws IOException;
}
