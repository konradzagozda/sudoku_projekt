package komponentowe.zadanie2;

import java.sql.SQLException;

public class SudokuSQLException extends SQLException {
    public SudokuSQLException() {
        super();
    }

    public SudokuSQLException(String reason) {
        super(reason);
    }

    @Override
    public String getLocalizedMessage() {
        return "SQLException occurred";
    }
}
