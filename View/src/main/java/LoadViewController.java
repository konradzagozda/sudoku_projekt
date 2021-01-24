import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import komponentowe.zadanie2.JdbcSudokuBoardDao;
import komponentowe.zadanie2.SaveObject;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoadViewController implements Initializable {
    public Button loadBtn;
    public ListView listView;

    private GameController gameController;
    private String[] names;
    private SaveObject saveObject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try(JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao("test")) {
            names = dao.getSudokuNames();
            listView.getItems().addAll(names);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(ActionEvent actionEvent) {
        var selected = listView.getSelectionModel().getSelectedItem().toString();

        try(JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(selected)) {
            saveObject = dao.read();
            gameController.replaceSaveObject(saveObject);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) loadBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void initData(GameController gameController) {
        this.gameController = gameController;
    }
}
