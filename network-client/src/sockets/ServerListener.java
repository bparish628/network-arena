package sockets;

import common.Controller;
import common.GameUpdate;
import common.Player;
import javafx.application.Platform;
import javafx.concurrent.Task;
import run.App;
import java.io.ObjectInputStream;
import java.rmi.server.ExportException;

public class ServerListener{
    private Object message = new Object();
    private Player[] playerUpdate;
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
                            /*Value to indicate all players are in*/
                            if(message instanceof Player[]) {
                                Controller.updatePlayers((Player[]) message);
                            }
                            App.goToStage(3);
                        }
                    });
                }

                while(message != null) {
                    message = in.readObject();

                    /*Value to indicate all players are in*/
                    if(message instanceof Player[]) {
                        System.out.println(((Player[]) message)[1].getUsername());
                        Platform.runLater(new Runnable() {
                            @Override
                        public void run() {

                            }
                        });
                    }
                }
            } catch(Exception e) {
                System.out.println("ERROR");
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
