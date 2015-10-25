package run;

import common.Controller;
import common.GameUpdate;
import fight.FightView;
import login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;
import sockets.ServerListener;
import sockets.SocketConnector;
import waiting.WaitView;

public class App extends Application {

    public static Stage appStage;

    private static int appState;
    public static void main(String[] args) {
        appState = 0;
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        appStage = stage;

        /*Set Application Title*/
        setTitle("The Network Arena");
        /*Go to Login View*/
        goToStage(1);
    }

    public static void goToStage(int num){
        switch(num){
            case 1:
                appState = 1;
                appStage.setScene(new LoginView().getPage());
                break;
            case 2:
                appState = 2;
                setTitle("Waiting for more players");
                appStage.setScene(new WaitView().getPage());
                break;
            case 3:
                appState = 3;
                setTitle("The Network Arena");
                appStage.setScene(new FightView().getPage());
                break;
        }
        appStage.show();
    }

    public static void updateGameState(GameUpdate gu) {
        if(appState == 3) {
            System.out.println(gu); //ugh I don't know.  This isn't where this should go, it needs to call somewhere else.
            Controller.updatePlayers(gu);
            Controller.listenerActivate();
        }
    }

    private static void setTitle(String title){
        appStage.setTitle(title);
    }
}
