package i18n;

import java.util.ListResourceBundle;

public class DifficultyLevelsBundle_pl_PL extends ListResourceBundle {
    private final Object[][] resources = {
            {"Easy", "Latwy"}, {"Medium", "Sredni"}, {"Hard", "Trudny"}
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
