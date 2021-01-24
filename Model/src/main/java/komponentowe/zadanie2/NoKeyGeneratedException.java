package komponentowe.zadanie2;

public class NoKeyGeneratedException extends SudokuSQLException {
    public NoKeyGeneratedException(String s) {
    }

    public NoKeyGeneratedException() {
    }

    @Override
    public String getLocalizedMessage() {
        return localisedMessages.getString("noKeyGenerated");
    }
}
