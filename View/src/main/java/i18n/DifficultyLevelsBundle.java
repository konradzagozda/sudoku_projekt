package i18n;

import java.util.ListResourceBundle;

public class DifficultyLevelsBundle extends ListResourceBundle {
    private final Object[][] resources = {
            {"Easy", "Easy"}, {"Medium", "Medium"}, {"Hard", "Hard"}
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
