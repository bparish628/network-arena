package ui;

import common.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import ui.fight.FightController;
import ui.login.LoginController;

public class EventsController extends Controller{

    public static void connectButton(LoginController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            if ((!ctrl.getUsernameField().getText().isEmpty() && ctrl.getClasses().getSelectedToggle() != null)) {
                getUser().setUsername(ctrl.getUsernameField().getText());
                getUser().setSelectedClass(ctrl.getClasses().getSelectedToggle().getUserData().toString());
                new App().goToStage(2);
            }
        });
    }

    public static void attackButton(FightController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            System.out.println("Attack Triggered!");
        });
    }

    public static void defendButton(FightController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            System.out.println("Defend Triggered!");
        });
    }

    public static void specialButton(FightController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            System.out.println("Special Triggered!");
        });
    }
}
