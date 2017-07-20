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
    public static String userid;
    /*
     * The default javafx stylesheet is com/sun/javafx/scene/control/skin/modena/modena.css
     * Older version is com/sun/javafx/scene/control/skin/caspian/caspian.css
     * The custom css for this project is view/darkstyle.css
     */
    private static String stylesheetPath = "view/darkstyle.css";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        primaryStage.setTitle("Project manager");
        Scene loginView = new Scene(root, 400, 600);
        loginView.getStylesheets().add(getStylesheetPath());
        primaryStage.setScene(loginView);
        db = new Database();

        primaryStage.show();
    }

    public static Database getDatabase(){
        return db;
    }

    public static String getStylesheetPath() {
        return stylesheetPath;
    }
}
