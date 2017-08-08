package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectInfoView extends VBox implements Initializable {

    @FXML private Label projectTitle;

    public ProjectInfoView(Project project){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectInfoView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        projectTitle.setText(project.getTitle());
    }

    public ProjectInfoView(){
        this.setAlignment(Pos.CENTER);
        Label infoText = new Label("Select a project.");
 
        this.getChildren().add(infoText);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
