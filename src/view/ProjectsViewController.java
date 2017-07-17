package view;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ListableItem;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;


/**
 * Created by Alvar on 12.6.2017.
 */
public class ProjectsViewController implements Initializable {

    @FXML public VBox projectsContainer;

    private static ArrayList<Project> projects = new ArrayList<Project>();
    private static boolean isStartup = true;

    private Database db = Main.getDatabase();
    private String userid = Main.userid;

    @FXML public BorderPane layout;
    @FXML public Button newProjectButton;
    @FXML public Button signOutButton;

    private void loadTasksView(Project project) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tasksView.fxml"));
        Parent root = loader.load(); //this triggers taskViewController's init-method

        TasksViewController tasksViewController = loader.getController();
        tasksViewController.setProjectData(project);

        Scene tasksViewScene = new Scene(root);
        tasksViewScene.getStylesheets().add(Main.getStylesheetPath());
        Stage stage = (Stage)projectsContainer.getScene().getWindow();
        stage.setScene(tasksViewScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.layout.getScene().getStylesheets().add(Main.getStylesheetPath());

        ArrayList<Project> projects = new ArrayList<>();
        try {
            projects = db.getProjects(userid);
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }

        for (ListableItem project : projects) {
            ListItem listItem = new ListItem(project) {
                @Override
                protected void viewButtonClicked() throws IOException {
                    loadTasksView((Project)this.item); //in this context we know that the item is a project
                }

                @Override
                protected void deleteButtonClicked() {
                    System.out.println("overriding");
                }
            };
            this.projectsContainer.getChildren().add(listItem);
        }
    }

    public void onAddButtonHover(){
        newProjectButton.setStyle("-fx-background-color: linear-gradient(#555555, #444444)");

    }
    public void onAddButtonHoverOut(){
        newProjectButton.setStyle("-fx-background-color: linear-gradient(#444444, #333333)");
    }

    public void onSignOutButtonHover(){
        signOutButton.setStyle("-fx-background-color: linear-gradient(#555555, #444444)");

    }
    public void onSignOutButtonHoverOut(){
        signOutButton.setStyle("-fx-background-color: linear-gradient(#444444, #333333)");
    }

    public void signOut(){
        try {
            // Empty the user id in main and return to login view
            Main.userid = "";

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();


            Scene login = new Scene(root);
            login.getStylesheets().add(Main.getStylesheetPath());
            Stage stage = (Stage) layout.getScene().getWindow();
            stage.setScene(login);
        }catch(IOException e){
                e.printStackTrace();
        }

    }

    public void addProject(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewProjectView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)layout.getScene().getWindow();
            Scene newProject = new Scene(root);
            newProject.getStylesheets().add(Main.getStylesheetPath());
            stage.setScene(newProject);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}


