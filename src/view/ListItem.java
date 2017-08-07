package view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.ListableItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created b
 *                                                                                      qwy Alvar on 22.6.2017.
 * A controller of a general list item in a UI. A list item could
 * be e.g. the ui-element of a project in the projects view or a task in the tasks view.
 *
 * The code is modified from the official documentation at:
 * http://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm
 */
public abstract class ListItem extends HBox implements Initializable{
    public ListableItem item;
    @FXML
    private Label listItemTitle;
    @FXML private Button backButton;
    @FXML private Button viewButton;

    public ListItem(ListableItem item) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "listItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.item = item;
        this.listItemTitle.setText(item.getTitle());
    }
/*
    public void setText(String value) {
        this.title.setText(value);
    }
*/
    @FXML
    abstract protected void onClick() throws IOException;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setId("listItem");
        this.getStylesheets().add(Main.getStylesheetPath());
    }
}
