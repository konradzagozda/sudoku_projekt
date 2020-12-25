import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Locale locale = Locale.getDefault();
        LanguageSettings languageSettings = new LanguageSettings(locale);
        ResourceBundle bundle = languageSettings.getGameBundle();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "mainView.fxml"
                ), bundle);

        primaryStage.setScene(new Scene(loader.load(), 600, 600));
        primaryStage.setTitle("Sudoku");

        // get MainController and pass info about languageSettings:
        MainController mainController = loader.getController();
        mainController.initData(languageSettings, primaryStage);

        primaryStage.show();
    }
}
