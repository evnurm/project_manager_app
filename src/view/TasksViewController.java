package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 9.6.2017.
 */
public class TasksViewController implements Initializable{

    private ProjectsViewController projectsViewController;
    private Project project;
    private ArrayList<Task> testTasks = new ArrayList<Task>();

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
        //taskContainer.getChildren().clear();
    }

    public void setProjectsViewController(ProjectsViewController c) {
        this.projectsViewController = c;
    }

    public void setProjectData(Project project) {
        this.taskContainer.getChildren().clear();
        this.project = project;
        for (Task task : this.project.tasks) {
            taskContainer.getChildren().add(new Label(task.title));
        }
    }
}
