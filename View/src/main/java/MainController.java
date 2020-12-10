import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Stage switchSceneToGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"));
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
}
