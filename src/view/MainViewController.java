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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        usernameLabel.setText(Main.getSession().getUser().getFirstName() + " " + Main.getSession().getUser().getLastName());
        profilePic.setImage(new Image((new File("res/default.jpg")).toURI().toString()));
        projectInfoContainer.getChildren().add(new ProjectInfoView());
        try {
            ArrayList<Project> projects = Main.getDatabase().getProjects(Main.getSession().getUser().getId());
            for(Project pr : projects){
                ListItem listItem = new ListItem(pr) {
                    @Override
                    protected void onClick() throws IOException {
                        Main.getSession().setProjectId(pr.getId());
                        projectInfoContainer.getChildren().clear();
                        projectInfoContainer.getChildren().add(new ProjectInfoView(pr));

                    }
                };

                listItem.setId("listItem");
                projectMenu.getChildren().add(listItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




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
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
