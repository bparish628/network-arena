package ui.login;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.login.LoginController;
import ui.login.User;

public class LoginView{

    GridPane container = new GridPane();
    private Scene view = new Scene(container, 400, 500);
    private LoginController LoginCtrl = new LoginController();

    public LoginView() {
        /*Setting the view parameters*/
        for(int i = 0; i < 3; i++){
            container.getColumnConstraints().add(new ColumnConstraints(100));
        }
        container.setAlignment(Pos.TOP_CENTER);
        container.setHgap(10);
        container.setVgap(10);
        container.setPadding(new Insets(15, 15, 15, 15));

        /*Setting up the title pane*/
        container.add(LoginCtrl.getTitle(), 0, 0, 3, 1);

        /*Setting up the username pane*/
        HBox usernamePane = new HBox();
        usernamePane.setPadding(new Insets(0, 45, 0, 0));
        usernamePane.setSpacing(10);
        usernamePane.getChildren().addAll(LoginCtrl.getUsernameLabel(), LoginCtrl.getUsernameField());
        container.add(usernamePane, 0, 2, 3, 1);

        /*Setting up class selection*/
        HBox classesPane = new HBox();
        classesPane.setPadding(new Insets(15, 45, 15, 45));
        classesPane.setSpacing(10);

        /*Radio Buttons*/
        ToggleGroup classes = LoginCtrl.getClasses();
        classesPane.getChildren().addAll(LoginCtrl.getToggleButton("Melee"),
                LoginCtrl.getToggleButton("Magic"),
                LoginCtrl.getToggleButton("Range"));
        container.add(classesPane, 0, 3, 3, 1);

        /*Setting up the stats labels*/
        container.add(LoginCtrl.getSelectedLabel(), 0, 4);
        container.add(LoginCtrl.getSelectedDisplay(), 1, 4);

        container.add(LoginCtrl.getHpLabel(), 0, 5);
        container.add(LoginCtrl.getHpDisplay(), 1, 5);

        container.add(LoginCtrl.getActionLabel(), 0, 6);
        container.add(LoginCtrl.getActionDisplay(1), 1, 6);
        container.add(LoginCtrl.getActionDisplay(2), 1, 7);
        container.add(LoginCtrl.getActionDisplay(3), 1, 8);

        container.add(LoginCtrl.getImageDisplay(), 2, 5, 1, 3);

        /*Setting up the host field*/
        HBox hostPane = new HBox();
        hostPane.setPadding(new Insets(30, 0, 45, 0));
        hostPane.setSpacing(10);

        hostPane.getChildren().addAll(LoginCtrl.getHostLabel(), LoginCtrl.getHostField());
        container.add(hostPane, 0, 9, 3, 1);

        /*Setting up connect button*/
        HBox boxBtn = new HBox(10);
        boxBtn.setAlignment(Pos.BOTTOM_CENTER);
        boxBtn.getChildren().add(LoginCtrl.getConnectButton());
        container.add(boxBtn, 1, 15);
    }

    public Scene getPage(){
        return view;
    }


}
