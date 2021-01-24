package komponentowe.zadanie2;

import java.util.ResourceBundle;

public class WrongSudokuStateException extends Exception {
    ResourceBundle localisedMessages = ResourceBundle.getBundle("exceptionBundle");
    public WrongSudokuStateException(String s) {
        super(s);
    }

    @Override
    public String getLocalizedMessage() {
        return localisedMessages.getString("SudokuStateException");
    }
}
