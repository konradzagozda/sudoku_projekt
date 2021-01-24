package komponentowe.zadanie2;

import java.io.IOException;
import java.sql.SQLException;

public interface Dao<T> extends AutoCloseable {

    T read() throws IOException, ClassNotFoundException, SQLException;

    void write(T obj) throws Exception;

}
