package exceptions;

public class NoSuchFileException extends RendererAplicationException {
    public NoSuchFileException() {
    }

    public NoSuchFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchFileException(String s) {
        super(s);
    }
}
