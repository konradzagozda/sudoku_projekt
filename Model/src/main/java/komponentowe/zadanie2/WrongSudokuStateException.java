package komponentowe.zadanie2;

public class WrongSudokuStateException extends Exception {
    public WrongSudokuStateException(String s) {
        super(s);
    }

    @Override
    public String getLocalizedMessage() {
        return "Generated board is corrupted";
    }
}
