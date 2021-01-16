import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.info("Entering application.");

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
        logger.info("Window should be visible.");
    }
}
