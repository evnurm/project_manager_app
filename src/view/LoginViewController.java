package view;

import controller.Database;
import controller.Session;
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
   @FXML public Label feedbackLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layout.getStyleClass().add("containerWithGradient");
        signUpLabel.getStyleClass().add("highlightLabel");
    }
    public void signIn(){
        Session session = Main.getSession();
        session.setUserId(db.signIn(usernameField.getText(), password.getText()));
        if(session.getUserId().equals("")){
            feedbackLabel.setText("Incorrect login credentials.");
        }else{
            try {
                Scene login = new Scene(FXMLLoader.load(getClass().getResource("mainView.fxml")));
                login.getStylesheets().add(Main.getStylesheetPath());
                Stage stage = (Stage) layout.getScene().getWindow();
                stage.setScene(login);
            }catch(IOException e){
                System.out.println("IO Exception.");
            }

        }

    }

    public void changeRegistrationView() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegistrationView.fxml"));
        Parent root = loader.load();



        Scene registration = new Scene(root);
        registration.getStylesheets().add(Main.getStylesheetPath());
        Stage stage = (Stage) layout.getScene().getWindow();
        stage.setScene(registration);



    }
}
