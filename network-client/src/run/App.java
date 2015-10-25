package run;

import fight.FightView;
import login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;
import sockets.ServerListener;
import waiting.WaitView;

public class App extends Application {

    public static Stage appStage;

    public static void main(String[] args) {
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
                appStage.setScene(new LoginView().getPage());
                break;
            case 2:
                setTitle("Waiting for more players");
                appStage.setScene(new WaitView().getPage());
                break;
            case 3:
                setTitle("The Network Arena");
                appStage.setScene(new FightView().getPage());
                break;
        }
        appStage.show();
    }

    private static void setTitle(String title){
        appStage.setTitle(title);
    }
}
