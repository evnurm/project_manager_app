package view;
/**
 * Created by Alvar on 9.6.2017.
 */

import controller.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class Main extends Application {

    private static  Database db;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        primaryStage.setTitle("Project manager");

        primaryStage.setScene(new Scene(root, 400, 600));
        db = new Database();

        primaryStage.show();
    }

    public static Database getDatabase(){
        return db;
    }
}
