package view;/**
 * Created by Alvar on 9.6.2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/viewTest.fxml"));
        primaryStage.setTitle("Project manager");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
