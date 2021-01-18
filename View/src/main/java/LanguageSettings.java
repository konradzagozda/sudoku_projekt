import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Komponent zarzadzajacy ustawieniami jezyka
 */
public class LanguageSettings {
    private Locale locale;
    private ResourceBundle difficultyBundle;
    private ResourceBundle gameBundle;
    private ResourceBundle messagesBundle;

    public LanguageSettings(Locale locale) {
        setLocale(locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;

        difficultyBundle = ResourceBundle.getBundle("i18n.DifficultyLevelsBundle", locale);
        messagesBundle = ResourceBundle.getBundle("MessagesBundle", locale);
        gameBundle = ResourceBundle.getBundle("GameBundle", locale);
    }

    public ResourceBundle getDifficultyBundle() {
        return difficultyBundle;
    }

    public ResourceBundle getGameBundle() {
        return gameBundle;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }
}
