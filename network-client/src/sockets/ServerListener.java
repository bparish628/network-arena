package sockets;

import common.Controller;
import common.Player;
import javafx.application.Platform;
import javafx.concurrent.Task;
import run.App;
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

                    /*set all players*/
                    if(message instanceof Player[]) {
                        System.out.println(((Player[]) message)[1].getUsername());
                        Platform.runLater(new Runnable() {
                            @Override
                        public void run() {
                                Controller.updatePlayers((Player[]) message);
                            }
                        });
                    }
                    /*Action/Target*/
                    if(message instanceof String && ((String)message).equals("Your turn")) {
                        System.out.println("yourTurn");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
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
