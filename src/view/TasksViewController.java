package view;

import controller.Database;
import controller.Session;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 9.6.2017.
 */
public class TasksViewController implements Initializable{

    private Project project;
    private Database db = Main.getDatabase();
    private Session session = Main.getSession();
    private String userid = session.getUserId();
    private String projectId = session.getProjectId();

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

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("NewTaskView.fxml"));
        Parent root =  fxmlLoader.load();
        NewTaskViewController controller = fxmlLoader.<NewTaskViewController>getController();
        controller.setOldStage((Stage) titleLabel.getScene().getWindow());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            tasks = db.getTasks(projectId);

        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }

        for (ListableItem task : tasks) {
            ListItem listItem = new ListItem(task) {
                @Override
                protected void viewButtonClicked() throws IOException {


                }

                @Override
                protected void deleteButtonClicked() {
                    try {
                        db.deleteTask(((Task)task).getTaskId());
                        Parent root = FXMLLoader.load(getClass().getResource("tasksView.fxml"));
                        Stage stage = (Stage) backButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            this.taskContainer.getChildren().add(listItem);
        }
    }


}
