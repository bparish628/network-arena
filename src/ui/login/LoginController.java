package ui.login;

import common.Controller;
import common.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import ui.EventsController;

public class LoginController extends Controller {
    /*User*/
    private Player user;

    /*Stats*/
    private Label selectedDisplay;
    private Label hpDisplay;
    private Label attackDisplay;
    private Label defenseDisplay;
    private Label action1Display;
    private Label action2Display;
    private Label action3Display;
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
        user = getUser();
        selectedDisplay = new Label();
        hpDisplay = new Label();
        attackDisplay = new Label();
        defenseDisplay = new Label();
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
        EventsController.connectButton(this, connectButton);

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
                    attackDisplay.setText(Integer.toString(user.getSelectedClass().getAttack()));
                    defenseDisplay.setText(Integer.toString(user.getSelectedClass().getDefense()));
                    action1Display.setText(user.getSelectedClass().getBasicAction().getName());
                    action2Display.setText(user.getSelectedClass().getDefendAction().getName());
                    action3Display.setText(user.getSelectedClass().getSpecialAction().getName());

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

    public Label getAttackLabel(){
        return new Label("Attack:");
    }

    public Label getDefenseLabel(){
        return new Label("Defense:");
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

    public Label getAttackDisplay(){
        return attackDisplay;
    }

    public Label getDefenseDisplay(){
        return defenseDisplay;
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
