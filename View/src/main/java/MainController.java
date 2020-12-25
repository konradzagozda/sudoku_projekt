import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import i18n.DifficultyLevelsBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import komponentowe.zadanie2.DifficultyLevel;


public class MainController implements Initializable {
    public Button playBtn;
    public RadioButton easyBtn;
    public RadioButton mediumBtn;
    public RadioButton hardBtn;
    public ToggleGroup difficultyToggle;
    public DifficultyLevel level = DifficultyLevel.EASY;
    public ResourceBundle difficultyLevelsBundle = ResourceBundle.getBundle("i18n.DifficultyLevelsBundle");
    public LanguageSettings languageSettings;
    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Stage switchSceneToGame(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = languageSettings.getGameBundle();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"), bundle);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );


        GameController controller = loader.getController();
        controller.initData(level, languageSettings);

        stage.show();
        return stage;
    }

    public void changeDifficulty(ActionEvent actionEvent) {
        RadioButton selectedRadioButton = (RadioButton) difficultyToggle.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        if (toggleGroupValue.equalsIgnoreCase(languageSettings.getDifficultyBundle().getString("Easy"))) {
            level = DifficultyLevel.EASY;
        } else if (toggleGroupValue.equalsIgnoreCase(languageSettings.getDifficultyBundle().getString("Medium"))) {
            level = DifficultyLevel.MEDIUM;
        } else {
            level = DifficultyLevel.HARD;
        }
    }

    public void switchLanguageToPolish(ActionEvent actionEvent) throws IOException {
        languageSettings.setLocale(new Locale("pl", "PL"));

        reOpenStage();
    }

    public void switchLanguageToEnglish(ActionEvent actionEvent) throws IOException {
        languageSettings.setLocale(new Locale("en", "US"));

        reOpenStage();
    }

    public void initData(LanguageSettings languageSettings, Stage primaryStage) {
        this.languageSettings = languageSettings;
        this.primaryStage = primaryStage;
    }

    public void reOpenStage() throws IOException {
        primaryStage.close();


        ResourceBundle bundle = languageSettings.getGameBundle();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"), bundle);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );

        MainController controller = loader.getController();
        controller.initData(languageSettings, stage);
        controller.changeDifficulties(languageSettings);
        stage.show();
    }

    public void changeDifficulties(LanguageSettings languageSettings) {
        difficultyLevelsBundle = languageSettings.getDifficultyBundle();
        easyBtn.setText(difficultyLevelsBundle.getString("Easy"));
        mediumBtn.setText(difficultyLevelsBundle.getString("Medium"));
        hardBtn.setText(difficultyLevelsBundle.getString("Hard"));
    }
}
