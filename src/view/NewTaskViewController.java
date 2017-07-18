package view;

import controller.Database;
import controller.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Viljami on 17.7.2017.
 */
public class NewTaskViewController implements Initializable {

    @FXML public Button addTaskButton;
    @FXML public TextField nameField;
    @FXML public TextArea descriptionField;
    @FXML public TextField deadlineField;

    Database db  = Main.getDatabase();
    Session session = Main.getSession();
    String projectId = session.getProjectId();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addTask(){

        String name = nameField.getText();
        String description = descriptionField.getText();
        String deadline = deadlineField.getText();
        try {
            db.addTask(projectId, name, description, deadline );
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            Stage stage  = (Stage) addTaskButton.getScene().getWindow();
            stage.close();
        }


    }
}
