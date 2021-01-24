import exceptions.LocalisedNoSuchMethodException;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import komponentowe.zadanie2.BacktrackingSudokuSolver;
import komponentowe.zadanie2.DifficultyLevel;
import komponentowe.zadanie2.SaveObject;
import komponentowe.zadanie2.SudokuBoard;
import komponentowe.zadanie2.SudokuField;
import komponentowe.zadanie2.WrongSudokuStateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class GameController implements Initializable {
    private static final Logger logger = LogManager.getLogger(GameController.class);

    public GridPane sudokuGrid;
    public TextField[][] textFields;
    public DifficultyLevel level;
    public SudokuBoard board;
    public SudokuField[][] fields;
    private LanguageSettings languageSettings;
    private SaveObject saveObject;
    private ResourceBundle localisedMessages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localisedMessages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());


        logger.info(localisedMessages.getString("initialization"));
        createEmptySudokuBoard();
    }

    private void bindToCurrentFields() {
        class SudokuFieldStringConverter extends IntegerStringConverter {
            @Override
            public String toString(Integer value) {
                if (value == 0) {
                    return "";
                }
                return super.toString(value);
            }
        }

        StringConverter converter = new SudokuFieldStringConverter();

        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                var textField = textFields[i][j];
                try {
                    JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
                    JavaBeanIntegerProperty integerProperty = builder.bean(fields[j][i]).name("fieldValue").build();
                    textField.textProperty().bindBidirectional(integerProperty, converter);

                    textField.textProperty().addListener((observable, oldValue, newValue) -> {

                        if (!newValue.matches("^\\d$") || newValue.equals("")) {
                            textField.textProperty().set("");
                            logger.debug(localisedMessages.getString("txtFldNoChange") + " " + newValue);
                            return;
                        }

                        logger.debug(localisedMessages.getString("txtFldChange") + oldValue + " to " + newValue);
                        logger.debug(localisedMessages.getString("saveObjectState") + saveObject);
                        if (board.isFull()) {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            if (board.checkBoard()) {
                                a.setContentText(null);
                                logger.info(localisedMessages.getString("solvedOK"));
                                a.setHeaderText(languageSettings.getGameBundle().getString("congratulations"));
                            } else {
                                a.setContentText(null);
                                logger.info(localisedMessages.getString("solvedBAD"));
                                a.setHeaderText(languageSettings.getGameBundle().getString("niestety"));
                            }
                            a.show();
                        }
                    });

                } catch (NoSuchMethodException ex) {
                    LocalisedNoSuchMethodException e = (LocalisedNoSuchMethodException) ex;
                    logger.error(e.getLocalizedMessage());
                }
            }
        }

    }

    private void createEmptySudokuBoard() {
        textFields = new TextField[9][9];

        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                textFields[i][j] = new TextField("");
                textFields[i][j].setAlignment(Pos.CENTER);
                textFields[i][j].setPrefHeight(100);
                textFields[i][j].setPrefWidth(100);
                textFields[i][j].setEditable(true);
                sudokuGrid.add(textFields[i][j], i, j);
                if ((i + 1) % 3 == 0 && (i + 1) % 9 != 0) {
                    if ((j + 1) % 3 == 0 && (j + 1) % 9 != 0) {
                        textFields[i][j].getStyleClass().add("borderBottomRight");
                    } else {
                        textFields[i][j].getStyleClass().add("borderRight");
                    }
                } else if ((j + 1) % 3 == 0 && (j + 1) % 9 != 0) {
                    textFields[i][j].getStyleClass().add("borderBottom");
                }

            }

        }
        logger.debug(localisedMessages.getString("textFieldsGenerated"));
    }

    void initData(DifficultyLevel level, LanguageSettings languageSettings) {
        this.level = level;
        this.languageSettings = languageSettings;
        this.localisedMessages = languageSettings.getMessagesBundle();
    }


    void initBoard() throws WrongSudokuStateException {
        fields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new SudokuField();
            }
        }
        board = new SudokuBoard(fields, new BacktrackingSudokuSolver());

        board.solveGame();

        logger.debug(localisedMessages.getString("newBoardGenerated"));
    }


    public void loadSudokuBoard(ActionEvent actionEvent) {
        try {
            initBoard();
            level.deleteFields(board);
            saveObject = new SaveObject(board);
            logger.debug(localisedMessages.getString("saveObjCreated"));
            setTextFieldsToUneditable();
        } catch (WrongSudokuStateException | CloneNotSupportedException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(e.getStackTrace());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(localisedMessages.getString("corruptedBoard"));
            a.show();
        }
        logger.trace(localisedMessages.getString("boardOK"));
        logger.debug("\n" + board.toString());

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) != 0) {
                    textFields[i][j].setText(Integer.toString(board.get(i, j)));
                } else {
                    textFields[i][j].setEditable(true);
                }
            }
        }

        bindToCurrentFields();
    }

    public void loadSudokuBoardFromDisk(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadView.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );

        LoadViewController controller = loader.getController();
        controller.initData(this);

        stage.show();
    }

    public void saveCurrentSudokuBoard(ActionEvent actionEvent) throws Exception {
        // show save window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("saveView.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );


        SaveViewController controller = loader.getController();
        controller.initData(saveObject);

        stage.show();
    }

    void setTextFieldsToUneditable() {
        var originalBoard = saveObject.getOriginal();
        // clear
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j].setEditable(true);
                textFields[i][j].getStyleClass().remove("generated");
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (originalBoard.get(i, j) != 0) {
                    textFields[i][j].setEditable(false);
                    textFields[i][j].getStyleClass().add("generated");
                }
            }
        }
    }

    public void replaceSaveObject(SaveObject saveObject) {
        this.saveObject = saveObject;
        board = saveObject.getCurrent();
        fields = board.getBoard();
        bindToCurrentFields();
        setTextFieldsToUneditable();
    }

}
