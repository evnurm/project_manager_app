package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ListableItem;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;


/**
 * Created by Alvar on 12.6.2017.
 */
public class ProjectsViewController implements Initializable {

    @FXML public VBox projectsContainer;
    private ArrayList<Project> projects = new ArrayList<Project>();

    private void loadTasksView(Project project) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tasksView.fxml"));
        Parent root = loader.load(); //this triggers taskViewController's init-method

        TasksViewController tasksViewController = loader.getController();
        tasksViewController.setProjectData(project);

        Scene tasksViewScene = new Scene(root);
        Stage stage = (Stage)projectsContainer.getScene().getWindow();
        stage.setScene(tasksViewScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set testing data
        Project testProject = new Project("Testing project", new java.util.Date(), new java.util.Date());
        Task[] testTaskData = {new Task("tehtava1"), new Task("tehtava2"), new Task("tehtava3")};
        for (Task task : testTaskData) {
            testProject.addTask(task);
        }
        this.projects.add(testProject);

        for (ListableItem project : this.projects) {
            ListItem listItem = new ListItem(project) {
                @Override
                protected void buttonClicked() throws IOException {
                    loadTasksView((Project)this.item);
                }
            };
            this.projectsContainer.getChildren().add(listItem);
        }
    }
}
