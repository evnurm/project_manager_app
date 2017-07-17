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

    private Project project;

    @FXML public Button backButton;
    @FXML public VBox taskContainer;
    @FXML public Label titleLabel;
    @FXML public Label descriptionLabel;

    public void backButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("projectsView.fxml"));
        Scene projectsViewScene = new Scene(root);
        Stage stage = (Stage)taskContainer.getScene().getWindow();
        stage.setScene(projectsViewScene);
    }

    @FXML public void addTask() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("NewTaskView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setProjectData(Project project) {
        this.taskContainer.getChildren().clear();
        this.project = project;
        this.titleLabel.setText(project.getTitle());
        this.descriptionLabel.setText(project.getDescription());
        for (ListableItem task : this.project.tasks) {
            ListItem listItem = new ListItem(task) {
                @Override
                protected void viewButtonClicked() throws IOException {
                    //TODO: create task view, which this would load
                }

                @Override
                protected void deleteButtonClicked() {
                    project.removeTask((Task)this.item);
                    taskContainer.getChildren().remove(this);
                    //TODO: should this interact with the database?
                }
            };

            this.taskContainer.getChildren().add(listItem);
        }
    }
}
