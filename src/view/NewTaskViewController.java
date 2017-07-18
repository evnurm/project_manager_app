package view;

import controller.Database;
import controller.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Viljami on 17.7.2017.
 */
public class NewTaskViewController implements Initializable {

    @FXML public Button addTaskButton;
    @FXML public TextField nameField;
    @FXML public TextArea desccriptionField;
    @FXML public TextField deadlineField;

    Database db  = Main.getDatabase();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addTask(){



    }
}
