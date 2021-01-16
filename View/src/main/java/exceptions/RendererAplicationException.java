package exceptions;

public class RendererAplicationException extends Exception {
    public RendererAplicationException() {
        super();
    }

    public RendererAplicationException(String message) {
        super(message);
    }

    public RendererAplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
