package komponentowe.zadanie2;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public enum DifficultyLevel {
    EASY(70),
    MEDIUM(50),
    HARD(30);

    private int starterFields;
    private static final Logger logger = LogManager.getLogger(DifficultyLevel.class);

    DifficultyLevel(int starterFields) {
        this.starterFields = starterFields;
    }


    public SudokuBoard deleteFields(SudokuBoard board) {
        int deleteCount = 81 - this.starterFields;
        Random random = new Random();
        while (deleteCount > 0) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (board.get(x, y) != 0) {
                board.set(x, y, 0);
                deleteCount--;
            }
        }
        logger.debug("cleared " + (81 - this.starterFields) + " fields");
        return board;
    }
}
