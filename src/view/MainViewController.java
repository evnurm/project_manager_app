package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 4.8.2017.
 */
public class MainViewController implements Initializable {

    @FXML private Label usernameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(Main.getSession().getUser().getFirstName() + " " + Main.getSession().getUser().getLastName());


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
