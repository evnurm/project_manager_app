package view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Alvar on 22.6.2017.
 * A controller of a general list item in a UI. A list item could
 * be e.g. the ui-element of a project in the projects view or a task in the tasks view.
 *
 * The code is modified from the official documentation at:
 * http://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm
 */
public class ListItemController extends HBox {
    @FXML
    private TextField textField;

    public ListItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "listItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @FXML
    protected void doSomething() {
        System.out.println("The button was clicked!");
    }
}
