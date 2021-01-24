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

public class SaveViewController implements Initializable {
    public TextField nameInput;
    public ListView namesList;
    public Button saveBtn;

    private SaveObject saveObject;
    private String[] names;
    private String name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try(JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao("test")) {
            names = dao.getSudokuNames();
            namesList.getItems().addAll(names);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveToDatabase(ActionEvent actionEvent) {
        var selected = namesList.getSelectionModel().getSelectedItem();
        if (!nameInput.getText().isEmpty()) {
            name = nameInput.getText();
            try(JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(name)) {
                dao.write(saveObject);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        } else {
            name = selected.toString();
            try(JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(name)) {
                dao.update(saveObject);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }




    }

    public void initData(SaveObject saveObject) {
        this.saveObject = saveObject;
    }
}
