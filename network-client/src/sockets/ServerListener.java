package sockets;

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
                while(message != null) {
                    message = in.readObject();

                    /*Value to indicate all players are in*/
                    if (message == "Hi there.") {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                App.goToStage(3);
                            }
                        });
                    }
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
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
