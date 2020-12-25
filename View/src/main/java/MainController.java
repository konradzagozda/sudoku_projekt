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
    public Locale locale = new Locale("en", "US");
    public ResourceBundle difficultyLevelsBundle = ResourceBundle.getBundle("i18n.DifficultyLevelsBundle");
    public ResourceBundle generalBundle = ResourceBundle.getBundle("GameBundle");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Stage switchSceneToGame(ActionEvent actionEvent) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("GameBundle", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"), bundle);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );


        GameController controller = loader.getController();
        controller.initData(level);

        stage.show();
        return stage;
    }

    public void changeDifficulty(ActionEvent actionEvent) {
        RadioButton selectedRadioButton = (RadioButton) difficultyToggle.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        if (toggleGroupValue.equalsIgnoreCase("easy")) {
            level = DifficultyLevel.EASY;
        } else if (toggleGroupValue.equalsIgnoreCase("medium")) {
            level = DifficultyLevel.MEDIUM;
        } else {
            level = DifficultyLevel.HARD;
        }
    }

    public void switchLanguageToPolish(ActionEvent actionEvent) throws IOException {
        locale = new Locale("pl", "PL");
        difficultyLevelsBundle = ResourceBundle.getBundle("i18n.DifficultyLevelsBundle", locale);
        easyBtn.setText(difficultyLevelsBundle.getString("Easy"));
        mediumBtn.setText(difficultyLevelsBundle.getString("Medium"));
        hardBtn.setText(difficultyLevelsBundle.getString("Hard"));
        ResourceBundle bundle = ResourceBundle.getBundle("GameBundle", locale);
        Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"), bundle);
    }

    public void switchLanguageToEnglish(ActionEvent actionEvent) {
        locale = new Locale("en", "US");
        difficultyLevelsBundle = ResourceBundle.getBundle("i18n.DifficultyLevelsBundle", locale);
        easyBtn.setText(difficultyLevelsBundle.getString("Easy"));
        mediumBtn.setText(difficultyLevelsBundle.getString("Medium"));
        hardBtn.setText(difficultyLevelsBundle.getString("Hard"));
    }
}
