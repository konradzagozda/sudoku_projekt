import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Komponent zarzadzajacy ustawieniami jezyka
 */
public class LanguageSettings {
    private Locale locale;
    private ResourceBundle difficultyBundle;
    private ResourceBundle gameBundle;

    public LanguageSettings(Locale locale) {
        setLocale(locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;

        difficultyBundle = ResourceBundle.getBundle("i18n.DifficultyLevelsBundle", locale);
        gameBundle = ResourceBundle.getBundle("GameBundle", locale);
    }

    public ResourceBundle getDifficultyBundle() {
        return difficultyBundle;
    }

    public ResourceBundle getGameBundle() {
        return gameBundle;
    }
}
