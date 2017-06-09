package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alvar on 9.6.2017.
 */
public class Controller implements Initializable{
    public Button testButton;

    public void printShortString(ActionEvent e) {
        System.out.println("test string");
    }

    public void printLongerString(ActionEvent e) {
        System.out.println("slightly longer testing string");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialized controller");
    }
}
