package exceptions;

public class NoDataException extends RendererAplicationException {
    public NoDataException() {
        super("Data is empty. Nothing to render.");
    }

    @Override
    public String getLocalizedMessage() {
        return localisedMessages.getString("noDataInFile");
    }
}
