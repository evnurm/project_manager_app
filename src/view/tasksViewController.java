package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 9.6.2017.
 */
public class tasksViewController implements Initializable{

    ArrayList<Task> testTasks = new ArrayList<Task>();

    @FXML public Button backButton;
    @FXML public VBox taskContainer;

    public void backButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("projectsView.fxml"));
        Scene projectsViewScene = new Scene(root);
        Stage stage = (Stage)taskContainer.getScene().getWindow();
        stage.setScene(projectsViewScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("starting init");
        Task[] testData = {new Task("tehtava1"), new Task("tehtava2"), new Task("tehtava3")};
        for (Task task : testData) {
            testTasks.add(task);
        }
        taskContainer.getChildren().clear();
        for (Task task : testTasks) {
            taskContainer.getChildren().add(new Label(task.title));
        }
        System.out.println("initialized controller");
    }
}
