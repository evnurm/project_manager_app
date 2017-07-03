package view;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Viljami on 21.6.2017.
 */
public class LoginViewController implements Initializable {

    Database db = new Database();
    @FXML public VBox layout;
   @FXML public TextField usernameField;
   @FXML public PasswordField password;
   @FXML public Button loginButton;
   @FXML public Label signUpLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void signIn(){
        db.signIn(usernameField.getText(), password.getText());

    }

    public void onHover(){
        loginButton.setStyle("-fx-background-color: linear-gradient(#555555, #444444); -fx-max-width: 100");
    }
    public void onHoverOut(){
        loginButton.setStyle("-fx-background-color: linear-gradient(#444444, #333333); -fx-max-width: 100");
    }

    public void changeRegistrationView() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegistrationView.fxml"));
        Parent root = loader.load();



        Scene registration = new Scene(root);
        Stage stage = (Stage) layout.getScene().getWindow();
        stage.setScene(registration);



    }
}
