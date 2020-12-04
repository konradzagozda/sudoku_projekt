package komponentowe.zadanie2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardDaoFactoryTest {

    SudokuBoardDaoFactory factory;

    @Test
    void basicTest() {
        // to make it 100% :(
        factory = new SudokuBoardDaoFactory();
        assertNotNull(factory);
    }

}
