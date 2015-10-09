package ui.login;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ui.ApplicationController;
import ui.login.User;

import java.awt.*;

public class LoginController {
    /*User*/
    public User user;

    /*Stats*/
    private Label selectedDisplay = new Label();
    private Label hpDisplay = new Label();
    private Label action1Display = new Label();
    private Label action2Display = new Label();
    private Label action3Display = new Label();
    private ImageView imageDisplay;


    /*Fields*/
    private TextField usernameField;
    private TextField hostField;

    /*Listeners*/
    private ToggleGroup classes;
    private Button connectButton;

    /*Constructor to set everything up*/
    public LoginController(){
        imageDisplay = new ImageView();
        user = new User();
        selectedDisplay = new Label();
        hpDisplay = new Label();
        action1Display = new Label();
        action2Display = new Label();
        action3Display = new Label();

        setUsernameField();
        setHostField();
        setConnectButton();
        setClasses();
    }

    private void setUsernameField(){
        usernameField = new TextField();
        usernameField.setPrefWidth(175);
    }

    private void setConnectButton(){
        connectButton = new Button("Connect");
        connectButton.setPrefWidth(212);

        connectButton.setOnAction((ActionEvent e) -> {
            if ((!getUsernameField().getText().isEmpty() && !getHostField().getText().isEmpty() && getClasses().getSelectedToggle() != null)) {
                user.setUsername(getUsernameField().getText());
                user.setHostname(getHostField().getText());
                new ApplicationController().goToStage(2);
            }
        });
    }

    private void setHostField(){
        hostField = new TextField();
        hostField.setPrefWidth(212);
    }

    private void setClasses(){
        classes = new ToggleGroup();
        classes.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle oldToggle, Toggle newToggle) {
                if (classes.getSelectedToggle() != null) {
                    user.setSelectedClass(classes.getSelectedToggle().getUserData().toString());
                    imageDisplay.setImage(user.getSelectedClass().getImage());
                    selectedDisplay.setText(classes.getSelectedToggle().getUserData().toString());
                    hpDisplay.setText(Integer.toString(user.getSelectedClass().getHp()));
                    action1Display.setText(user.getSelectedClass().getAction1());
                    action2Display.setText(user.getSelectedClass().getAction2());
                    action3Display.setText(user.getSelectedClass().getAction3());

                }
            }
        });
    }

    public Label getTitle(){
        Label title = new Label("The Network Arena");
        title.setFont(new Font("Cambria", 32));
        title.setUnderline(true);
        return title;
    }

    public Label getUsernameLabel(){
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Cambria", 16));
        return usernameLabel;
    }

    public Label getSelectedLabel(){
        return new Label("Class:");
    }

    public Label getHpLabel(){
        return new Label("HP:");
    }

    public Label getActionLabel(){
        return new Label("Actions:");
    }

    public Label getHostLabel(){
        Label hostLabel = new Label("Host:");
        hostLabel.setFont(new Font("Cambria", 16));
        return hostLabel;
    }

    public Label getSelectedDisplay(){
        return selectedDisplay;
    }

    public Label getHpDisplay(){
        return hpDisplay;
    }

    public Label getActionDisplay(int num){
        switch(num){
            case 1:
                return action1Display;
            case 2:
                return action2Display;
            case 3:
                return action3Display;
            default:
                return new Label();
        }
    }

    public ImageView getImageDisplay(){
        return imageDisplay;
    }

    public TextField getUsernameField(){
        return usernameField;
    }

    public TextField getHostField(){
        return hostField;
    }

    public RadioButton getToggleButton(String data){
        RadioButton radio = new RadioButton(data);
        radio.setUserData(data);
        radio.setToggleGroup(classes);
        return radio;
    }

    public ToggleGroup getClasses(){
        return classes;
    }

    public Button getConnectButton(){
        return connectButton;
    }
}
