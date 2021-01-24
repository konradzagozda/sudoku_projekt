package komponentowe.zadanie2;

public class NoRowsAffectedException extends SudokuSQLException {
    public NoRowsAffectedException() {
    }

    public NoRowsAffectedException(String reason) {
        super(reason);
    }

    @Override
    public String getLocalizedMessage() {
        return localisedMessages.getString("noRowsAffected");
    }
}
