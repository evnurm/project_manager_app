package view;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Viljami on 21.6.2017.
 */
public class LoginViewController implements Initializable {

    Database db = new Database();
   @FXML public TextField usernameField;
   @FXML public PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void signIn(){
        db.signIn(usernameField.getText(), password.getText());

    }
}
