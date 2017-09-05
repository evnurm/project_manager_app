package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Member;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class MemberListItem extends HBox implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private ImageView memberProfilePic;


    public MemberListItem(Member member) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MemberListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            memberProfilePic.setImage(new Image(new File("res/default.jpg").toURI().toString()));
            nameLabel.setText(member.getFirstName() + " " + member.getLastName());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * An event handler for click events
     */
    public abstract void onClick();
}