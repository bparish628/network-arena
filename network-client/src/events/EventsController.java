package events;

import common.Controller;
import common.ServerRequest;
import fight.FightController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import login.LoginController;
import run.App;
import sockets.SocketConnector;

import java.io.ObjectOutputStream;

public class EventsController extends Controller{
    public static void connectButton(LoginController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            if ((!ctrl.getUsernameField().getText().isEmpty() && ctrl.getClasses().getSelectedToggle() != null)) {
                getUser().setUsername(ctrl.getUsernameField().getText());
                getUser().setSelectedClass(ctrl.getClasses().getSelectedToggle().getUserData().toString());
                initSocket();
                App.goToStage(2);
            }
        });
    }

    public static void attackButton(FightController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            if(getUser().isMyTurn() && getUser().getTarget().getUsername() != null){
                getUser().setQueuedAction(getUser().getSelectedClass().getBasicAction());
                ctrl.updateLog(getUser().getQueuedAction().getName() + " was activated!");
                try {
                    SocketConnector.send(new ServerRequest(getUser().getSelectedClass().getBasicAction(), getUser().getTarget().getPlayerNum(), getUser().getPlayerNum()));
                    getUser().setMyTurn(false);
                }catch(Exception exception){
                    System.out.println(exception);
                }
            }
        });
    }

    public static void defendButton(FightController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            if(getUser().isMyTurn() && getUser().getTarget().getUsername() != null){
                getUser().setQueuedAction(getUser().getSelectedClass().getDefendAction());
                ctrl.updateLog(getUser().getQueuedAction().getName() + " was activated!");
                try {
                    SocketConnector.send(new ServerRequest(getUser().getSelectedClass().getDefendAction(), getUser().getTarget().getPlayerNum(), getUser().getPlayerNum()));
                    getUser().setMyTurn(false);
                }catch(Exception exception){
                    System.out.println(exception);
                }
            }
        });
    }

    public static void specialButton(FightController ctrl, Button buttonNode){
        buttonNode.setOnAction((ActionEvent e) -> {
            if(getUser().isMyTurn() && getUser().getTarget().getUsername() != null){
                getUser().setQueuedAction(getUser().getSelectedClass().getSpecialAction());
                ctrl.updateLog(getUser().getQueuedAction().getName() + " was activated!");
                try {
                    SocketConnector.send(new ServerRequest(getUser().getSelectedClass().getSpecialAction(), getUser().getTarget().getPlayerNum(), getUser().getPlayerNum()));
                    getUser().setMyTurn(false);
                }catch(Exception exception){
                    System.out.println(exception);
                }
            }
        });
    }

    public static void targetSelector(FightController ctrl, VBox opponentsNode){
        for(int i = 0; i < 3; i++){
            final int playerNumber = i;
            Node oppNode = opponentsNode.getChildren().get(i);
            oppNode.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            getUser().setTarget(getPlayers()[playerNumber]);
                            for(int i = 0; i < 3; i++){
                                if(i == playerNumber){
                                    oppNode.setStyle(oppNode.getStyle() + " -fx-border-color: gold;");
                                    ctrl.updateTarget();
                                }else {
                                    opponentsNode.getChildren().get(i).setStyle(oppNode.getStyle() + " -fx-border-color: black;");
                                }
                            }
                            ctrl.updateLog("Target selected: " + getUser().getTarget().getUsername());
                        }
                    });
        }
    }
}
