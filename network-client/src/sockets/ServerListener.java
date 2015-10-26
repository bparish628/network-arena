package sockets;

import common.Controller;
import common.Player;
import fight.FightController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import run.App;

import javax.naming.ldap.Control;
import java.io.ObjectInputStream;

public class ServerListener{
    private Object message = new Object();
    private ObjectInputStream in;

    /*This task listens to the server*/
    private Task async = new Task<Void>() {
        @Override public Void call() {
            try {
                message = in.readObject();
                Controller.getUser().setPlayerNum((Integer) message);

                message = in.readObject();
                if ((message instanceof String) && ((String)message).equals("Hi there.")) {
                    message = in.readObject();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            /*set all players*/
                            if(message instanceof Player[]) {
                                Controller.updatePlayers((Player[]) message);
                            }
                            App.goToStage(3);
                        }
                    });
                }

                while(message != null) {
                    message = in.readObject();

                    /*Action/Target*/
                    if(message instanceof Player[]) {
                         /*set all players*/
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Controller.updatePlayers((Player[]) message);
                                if(!Controller.getUser().isAlive()) {
                                    App.goToStage(4);
                                }
                                if(!Controller.getPlayers()[0].isAlive() && !Controller.getPlayers()[1].isAlive() && !Controller.getPlayers()[2].isAlive()){
                                    App.goToStage(5);
                                }
                            }
                        });
                    }else if(message instanceof String && ((String)message).equals("Your turn")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                FightController.updateLog("It's my turn!");
                                Controller.getUser().setMyTurn(true);
                            }
                        });
                    }

                }
            } catch(Exception e) {
                System.out.println(e);
            }

            return null;
        }
    };

    public void listen(ObjectInputStream in){
        Platform.setImplicitExit(false);
        this.in = in;
        new Thread(async).start();
    }
}
