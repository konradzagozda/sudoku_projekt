import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import komponentowe.zadanie2.BacktrackingSudokuSolver;
import komponentowe.zadanie2.SudokuBoard;
import komponentowe.zadanie2.SudokuField;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("GameBundle", locale);
        Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"), bundle);

        primaryStage.setScene(new Scene(root, 600, 600));


        SudokuField[][] fields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new SudokuField();
            }
        }
        SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
        System.out.println(board.toString());
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }
}
