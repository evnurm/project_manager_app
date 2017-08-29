package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Project;
import model.User;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 4.8.2017.
 */
public class MainViewController implements Initializable {

    @FXML private Label usernameLabel;
    @FXML private VBox projectMenu;
    @FXML private StackPane projectInfoContainer;
    @FXML private ImageView profilePic;

    private ArrayList<Project> projects;
    private Project currentProject;
    private ProjectInfoView currentProjectInfoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(Main.getSession().getUser().getFirstName() + " " + Main.getSession().getUser().getLastName());
        profilePic.setImage(new Image((new File("res/default.jpg")).toURI().toString()));
        projectInfoContainer.getChildren().add(new ProjectInfoView());
        try {
            loadProjectsToView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * loadProjectsToView retrieves the projects from the database and creates
     * the project list in the UI according to that data
     * @throws SQLException
     */
    private void loadProjectsToView() throws SQLException {
        projectMenu.getChildren().clear();
        this.projects = Main.getDatabase().getProjects(Main.getSession().getUser().getId());
        for(Project pr : projects){
            ListItem listItem = new ListItem(pr) {
                @Override
                protected void onClick() throws IOException {
                    Main.getSession().setProjectId(pr.getId());
                    projectInfoContainer.getChildren().clear();
                    ProjectInfoView projectInfoView = new ProjectInfoView(pr, MainViewController.this);
                    projectInfoContainer.getChildren().add(projectInfoView);
                    currentProject = pr;
                    currentProjectInfoView = projectInfoView;
                }
            };
            listItem.setId("listItem");
            projectMenu.getChildren().add(listItem);
        }
    }

    /**
     * Tries to update the currently chosen project and its view
     */
    private void updateCurrentProject() {
        if ((this.currentProject != null) && (this.currentProjectInfoView != null)) {
            Project oldProject = this.currentProject;
            Project updatedOldProject = null;
            for (Project pr : this.projects) {
                if (pr.getId().equals(oldProject.getId())) {
                    updatedOldProject = pr;
                }
            }
            if (updatedOldProject != null) { //found matching updated project
                this.currentProject = updatedOldProject;
                this.projectInfoContainer.getChildren().clear();

                ProjectInfoView oldProjectInfoView = this.currentProjectInfoView;
                ProjectInfoView newProjectInfoView = new ProjectInfoView(this.currentProject, this);

                this.projectInfoContainer.getChildren().add(newProjectInfoView);
                newProjectInfoView.selectTab(oldProjectInfoView.getSelectedTabIndex());
                this.currentProjectInfoView = newProjectInfoView;
            }
            else {
                System.out.println("couldn't find the previously chosen project");
            }
        }
        else {
            System.out.println("no project chosen: no current project to reload");
        }
    }

    /**
     * refreshProjects refreshes project data and updates to UI to display the new data. This can be
     * called e.g. after the user makes a change in the program.
     */
    public void refreshProjects() {
        try{
            loadProjectsToView();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }
        updateCurrentProject();
    }

    public void logout(){
        try {
            Main.getSession().setUser(new User("", "", "", ""));
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("LoginView.fxml")));
            Stage stage = (Stage) usernameLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewProjectWindow(){

        try {
            Scene scene  = new Scene(FXMLLoader.load(getClass().getResource("NewProjectView.fxml")));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            this.refreshProjects();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
