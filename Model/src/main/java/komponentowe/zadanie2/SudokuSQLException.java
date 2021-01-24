package komponentowe.zadanie2;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class SudokuSQLException extends SQLException {
    ResourceBundle localisedMessages = ResourceBundle.getBundle("exceptionBundle");

    public SudokuSQLException() {
        super();
    }

    public SudokuSQLException(String reason) {
        super(reason);
    }

    @Override
    public String getLocalizedMessage() {
        return localisedMessages.getString("SQLException");
    }
}
