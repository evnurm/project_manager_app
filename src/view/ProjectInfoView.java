package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Member;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProjectInfoView extends VBox implements Initializable {

    @FXML private Label projectTitle;
    @FXML private Label projectLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label createdLabel;
    @FXML private Label deadlineLabel;
    @FXML private VBox taskMenu;
    @FXML private Label taskName;
    @FXML private Label taskDescription;
    @FXML private Label taskCreated;
    @FXML private Label taskDeadline;
    @FXML private AnchorPane taskInfoContainer;
    @FXML private VBox membersContainer;
    @FXML private TabPane tabPane;

    private MainViewController mainViewController;

    public ProjectInfoView(Project project, MainViewController mainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectInfoView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;

        projectTitle.setText(project.getTitle());
        projectLabel.setText(project.getTitle());
        descriptionLabel.setText(project.getDescription());
        createdLabel.setText(project.getCreated());
        deadlineLabel.setText(project.getDeadline());


        ArrayList<Task> tasks = project.tasks;
        if(tasks.size() == 0){
            taskMenu.getChildren().add(new Label("No tasks."));
            Label infoLabel = new Label("This project doesn't have any tasks yet. Why not create one?");
            infoLabel.setAlignment(Pos.CENTER);
            taskInfoContainer.getChildren().clear();
            taskInfoContainer.getChildren().add(infoLabel);
        }

        for(Task task: tasks){
            taskMenu.getChildren().add(new ListItem(task) {
                @Override
                protected void onClick() throws IOException {
                    taskName.setText(task.getTitle());
                    taskDescription.setText(task.getDescription());
                    taskCreated.setText(task.getCreated());
                    taskDeadline.setText(task.getDeadline());

                }
            });
        }
        ArrayList<Member> members = project.getMembers();
        for(Member m : members){
            membersContainer.getChildren().add(new MemberListItem(m));
        }
    }



    public ProjectInfoView(){
        this.setAlignment(Pos.CENTER);
        Label infoText = new Label("Select a project.");

        this.getChildren().add(infoText);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void openNewTaskWindow(){
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("NewTaskView.fxml")));
            stage.setScene(scene);
            stage.showAndWait();
            this.mainViewController.refreshProjects();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectTab(int tabIndex) {
        SelectionModel<Tab> selectionModel = this.tabPane.getSelectionModel();
        selectionModel.clearAndSelect(tabIndex);
    }

    public int getSelectedTabIndex() {
        SelectionModel<Tab> selectionModel = this.tabPane.getSelectionModel();
        return selectionModel.getSelectedIndex();
    }
}
