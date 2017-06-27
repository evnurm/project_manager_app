package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ListableItem;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 9.6.2017.
 */
public class TasksViewController implements Initializable{

    //private ProjectsViewController projectsViewController;
    private Project project;

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
    }

    //public void setProjectsViewController(ProjectsViewController c) {
    //    this.projectsViewController = c;
    //}

    public void setProjectData(Project project) {
        this.taskContainer.getChildren().clear();
        this.project = project;
        for (ListableItem task : this.project.tasks) {
            ListItem listItem = new ListItem(task) {
                @Override
                protected void buttonClicked() throws IOException {
                    super.buttonClicked(); //TODO: give custom behaviour
                }
            };
            this.taskContainer.getChildren().add(listItem);
        }
    }
}
