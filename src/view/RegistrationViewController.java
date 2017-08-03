package view;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;


import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Viljami on 1.7.2017.
 */
    public class RegistrationViewController implements Initializable{

        @FXML public VBox layout;
        @FXML public TextField username;
        @FXML public PasswordField password1;
        @FXML public PasswordField password2;
        @FXML public TextField firstName;
        @FXML public TextField lastName;
        @FXML public TextField email;
        @FXML public Button signUpButton;
        @FXML public Label feedbackLabel;
        @FXML public Label loginLabel;

        Database db = Main.getDatabase();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layout.getStyleClass().add("containerWithGradient");
        loginLabel.getStyleClass().add("highlightLabel");
    }

    public void changeLoginView() throws IOException {
        FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene login = new Scene( FXMLLoader.load(getClass().getResource("LoginView.fxml")));
        login.getStylesheets().add(Main.getStylesheetPath());
        Stage stage = (Stage) layout.getScene().getWindow();
        stage.setScene(login);

    }

    private boolean validatePasswords(){
        return password1.getText().equals(password2.getText());
    }

    /**
     * Takes care of registering the user in the database with the given data.
     */
    public void register(){

        // First gather all the information from the form

            String user = username.getText();
            String pword1 = password1.getText();
            String pword2 = password2.getText();
            String fname = firstName.getText();
            String lname = lastName.getText();
            String emailAddress = email.getText();

            // Set the color of the feedback text to red.
            feedbackLabel.setTextFill(Color.web("BC0001"));

        // Now check whether all of them are filled, i.e. not empty strings. Otherwise, tell user to fill all fields.

            if(!user.equals("") && !pword1.equals("") && !pword2.equals("") && !fname.equals("") && !lname.equals("") && !emailAddress.equals("")) {

                // Find out if the passwords given match.
                if (validatePasswords()) {

                    // Check that the email address is of a proper form, i.e. something@example.com
                    Pattern emailPattern = Pattern.compile("[A-Z0-9]+[.A-Z0-9]*+@[A-Z0-9]+.[A-Z]{2,3}", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = emailPattern.matcher(email.getText());
                    if (matcher.matches()) {



                        // Create an id for the database and see if it is available. If not, try again as long as it is.
                        String id = db.createID(6);
                        try {
                            // Check that the given username doesn't yet exist as that would possibly cause a username-password collision.
                            if(!db.usernameIsTaken(user)) {
                                while (db.idIsTaken(id)) {
                                    id = db.createID(6);

                                }
                                db.insertUser(id, user, pword1, fname, lname, emailAddress);
                                feedbackLabel.setTextFill(Color.GREEN);
                                feedbackLabel.setText("Congratulations! You are now registered.");

                            }else{
                                feedbackLabel.setText("Oops! the username you chose is already taken.");
                            }


                        }catch(SQLException e){
                            feedbackLabel.setText("Oops! Something went wrong.");
                        }



                    }else{
                        feedbackLabel.setText("Please give a proper email address.");
                    }

                }else{
                   feedbackLabel.setText("The passwords you entered do not match. Please give the same password twice.");
                }

            }else{
                feedbackLabel.setText("Please fill in all the fields.");


            }
    }


    }


