package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import javafx.scene.control.*;
import model.Member;
import model.Project;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MemberSearch implements Initializable{

    @FXML private VBox memberResults;
    @FXML private TextField searchBar;
    @FXML private Button searchButton;
    @FXML private Button addMemberButton;

    private User selectedUser;
    private Project project;

    public MemberSearch(Project project) {
        this.project = project;

    }

    public void updateResults(){
        if(!searchBar.getText().equals("")) {
            try {
                memberResults.setAlignment(Pos.TOP_CENTER);
                ArrayList<User> users = Main.getDatabase().searchUsers(searchBar.getText(), project.getId());
                memberResults.getChildren().clear();

                if(users.size() != 0) {
                    for (User user : users) {
                        memberResults.getChildren().add(new MemberListItem(new Member(user)){
                            @Override
                            public  void onClick(){
                                selectedUser  = user;
                                this.requestFocus();
                            }
                        });
                    }
                }else{
                    memberResults.setAlignment(Pos.CENTER);
                    memberResults.getChildren().add(new Label("No matching users found."));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{

            memberResults.getChildren().clear();
            memberResults.setAlignment(Pos.CENTER);
            memberResults.getChildren().add(new Label("No search term given."));
        }
    }

    public void addNewMember(){
        try {


                Main.getDatabase().addMember(project.getId(), selectedUser.getId());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}
