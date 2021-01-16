import exceptions.NoDataException;
import exceptions.NoSuchFileException;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import komponentowe.zadanie2.BacktrackingSudokuSolver;
import komponentowe.zadanie2.DifficultyLevel;
import komponentowe.zadanie2.FileSudokuBoardDao;
import komponentowe.zadanie2.SudokuBoard;
import komponentowe.zadanie2.SudokuBoardDaoFactory;
import komponentowe.zadanie2.SudokuField;
import komponentowe.zadanie2.WrongSudokuStateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {
    private static final Logger logger = LogManager.getLogger(GameController.class);

    public GridPane sudokuGrid;
    public TextField[][] textFields;
    public DifficultyLevel level;
    public SudokuBoard board;
    public Button loadBtn;
    public SudokuField[][] fields;
    private LanguageSettings languageSettings;
    private SaveObject saveObject;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("game view initialized");
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

            @Override
            public Integer fromString(String value) {
                if (value.isEmpty()) {
                    return 0;
                }
                return super.fromString(value);
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
                            logger.debug("textfield didn't change - wrong value was inserted:" + newValue);
                            return;
                        }

                        logger.debug("textfield changed from " + oldValue + " to " + newValue);
                        logger.debug("current state of saveObject: \n" + saveObject);
                        if (board.isFull()) {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            if (board.checkBoard()) {
                                a.setContentText(null);
                                logger.info("User solved sudoku correctly!");
                                a.setHeaderText(languageSettings.getGameBundle().getString("congratulations"));
                            } else {
                                a.setContentText(null);
                                logger.info("User solved sudoku incorrectly!");
                                a.setHeaderText(languageSettings.getGameBundle().getString("niestety"));
                            }
                            a.show();
                        }
                    });

                } catch (NoSuchMethodException ex) {
                    logger.error(ex.getStackTrace());
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
        logger.debug("9x9 textFields generated");
    }

    void initData(DifficultyLevel level, LanguageSettings languageSettings) {
        this.level = level;
        this.languageSettings = languageSettings;
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

        logger.debug("new board got generated!");
    }


    public void loadSudokuBoard(ActionEvent actionEvent) {
        try {
            initBoard();
            level.deleteFields(board);
            saveObject = new SaveObject(board);
            logger.debug("saveObject created");
            setTextFieldsToUneditable();
        } catch (WrongSudokuStateException | CloneNotSupportedException e) {
            logger.error("Generated board is corrupted, try again!");
            logger.error(e.getStackTrace());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Generated board is corrupted, try again!");
            a.show();
        }
        logger.trace("Generated board is ok!");
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

    public void loadSudokuBoardFromDisk(ActionEvent actionEvent) throws IOException, ClassNotFoundException, NoSuchFieldException, NoSuchFileException, NoDataException {
        FileChooser fileChooser = new FileChooser();
        var file = fileChooser.showOpenDialog(new Stage());
        try (FileSudokuBoardDao<SaveObject> dao = (FileSudokuBoardDao<SaveObject>) SudokuBoardDaoFactory.getFileDao(file)) {
            saveObject = dao.read();
            board = saveObject.getCurrent();
            fields = board.getBoard();
        } catch (IOException ioe) {
            logger.error(ioe.getStackTrace());
            if (!file.exists()){
                logger.error("no such file");
                throw new NoSuchFileException("No file with specified name: " + file.getName());
            } else if (file.length() == 0) {
                logger.error("no data in a file");
                throw new NoDataException();
            }
        }


        bindToCurrentFields();
        setTextFieldsToUneditable();
    }

    public void safeCurrentSudokuBoard(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        var file = fileChooser.showSaveDialog(new Stage());
        try(FileSudokuBoardDao<SaveObject> dao = (FileSudokuBoardDao<SaveObject>) SudokuBoardDaoFactory.getFileDao(file)) {
            dao.write(saveObject);
        }
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

}
