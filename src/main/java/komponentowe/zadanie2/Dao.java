package komponentowe.zadanie2;

public interface Dao {

    <T>T read();

    <T> void write(T obj);
}
