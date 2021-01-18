package exceptions;

import java.util.ResourceBundle;

public class LocalisedNoSuchMethodException extends NoSuchMethodException {

    ResourceBundle localisedMessages = ResourceBundle.getBundle("MessagesBundle");

    @Override
    public String getLocalizedMessage() {
        return localisedMessages.getString("noSuchMethod");
    }
}
