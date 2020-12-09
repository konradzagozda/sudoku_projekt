import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import komponentowe.zadanie2.BacktrackingSudokuSolver;
import komponentowe.zadanie2.SudokuBoard;
import komponentowe.zadanie2.SudokuField;
import komponentowe.zadanie2.WrongSudokuStateException;


public class GameController implements Initializable {
    public GridPane sudokuGrid;
    public Label[][] labels;
    public DifficultyLevel level;
    public SudokuBoard board;
    public Button loadBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void initData(DifficultyLevel level) {
        this.level = level;
    }

    SudokuBoard initBoard() throws WrongSudokuStateException {
        SudokuField[][] fields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new SudokuField();
            }
        }
        SudokuBoard board = new SudokuBoard(fields, new BacktrackingSudokuSolver());
        board.solveGame();
        this.board = board;
        return board;
    }


    void deleteSomeFields() {
        int howMany = 0;
        if (this.level == DifficultyLevel.EASY) {
            howMany = 10;
        } else if (this.level == DifficultyLevel.MEDIUM) {
            howMany = 40;
        } else if (this.level == DifficultyLevel.HARD){
            howMany = 70;
        }


        Random random = new Random();
        while (howMany > 0) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (board.get(x, y) != 0) {
                board.set(x, y, 0);
                howMany--;
            }
        }
    }

    public void loadSudokuBoard(ActionEvent actionEvent) {
        try {
            initBoard();
            deleteSomeFields();
        } catch (WrongSudokuStateException e) {
            e.printStackTrace();
        }

        labels = new Label[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) != 0) {
                    labels[i][j] = new Label(Integer.toString(board.get(i, j)));
                } else {
                    labels[i][j] = new Label("");
                }

                labels[i][j].setAlignment(Pos.CENTER);
                labels[i][j].setPrefHeight(100);
                labels[i][j].setPrefWidth(100);
                labels[i][j].setTextAlignment(TextAlignment.CENTER);
                sudokuGrid.add(labels[i][j], i, j);
            }
        }
        System.out.println(sudokuGrid.getChildren());


    }
}
