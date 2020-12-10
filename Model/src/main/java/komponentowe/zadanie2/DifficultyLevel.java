package komponentowe.zadanie2;

import java.util.Random;

public enum DifficultyLevel {
    EASY(70),
    MEDIUM(50),
    HARD(30);

    private int starterFields;

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
        return board;
    }
}
