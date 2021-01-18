package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class RendererAplicationException extends Exception {

    ResourceBundle localisedMessages = ResourceBundle.getBundle("MessagesBundle");

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
