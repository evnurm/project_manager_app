package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by Alvar on 12.6.2017.
 */
public class projectsViewController {

    public void viewButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tasksView.fxml"));
        Scene tasksViewScene = new Scene(root);
        Stage stage = (Stage)projectsContainer.getScene().getWindow();
        stage.setScene(tasksViewScene);
    }

    @FXML public VBox projectsContainer;

}
