package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Project;
import model.User;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(Main.getSession().getUser().getFirstName() + " " + Main.getSession().getUser().getLastName());
        try {
            ArrayList<Project> projects = Main.getDatabase().getProjects(Main.getSession().getUser().getId());
            for(Project pr : projects){
                projectMenu.getChildren().add(new ListItem(pr) {
                    @Override
                    protected void viewButtonClicked() throws IOException {

                    }

                    @Override
                    protected void deleteButtonClicked() {

                    }
                });
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


}
