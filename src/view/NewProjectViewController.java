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
    private Stage oldStage;

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


        } catch (SQLException e) {
            System.out.println("SQL Exception");

        }finally{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("projectsView.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene projects = new Scene(root);
            oldStage.setScene(projects);
            Stage stage = (Stage) newProjectButton.getScene().getWindow();
            stage.close();

        }


    }
    public void setOldStage(Stage oldStage){
        this.oldStage = oldStage;
    }
}
