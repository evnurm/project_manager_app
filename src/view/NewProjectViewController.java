package view;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Viljami on 6.7.2017.
 */
public class NewProjectViewController implements Initializable{

    @FXML public VBox layout;
    @FXML public TextField nameField;
    @FXML public TextArea descriptionField;
    @FXML public TextField deadlineField;
    @FXML public Button newProjectButton;

    private Database db = Main.getDatabase();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layout.getStyleClass().add("containerWithGradient");
    }

    public void createNewProject(){

        String name     = nameField.getText();
        String desc     = descriptionField.getText();
        String deadline = deadlineField.getText();

        try {
            db.addProject(name, desc, deadline);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("projectsView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) layout.getScene().getWindow();
            Scene projects = new Scene(root);
            stage.setScene(projects);

        } catch (SQLException e) {
            System.out.println("SQL Exception");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
