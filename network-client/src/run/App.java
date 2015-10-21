package run;

import fight.FightView;
import login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage appStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        appStage = stage;

        /*Set Application Title*/
        appStage.setTitle("The Network Arena");
        /*Go to Login View*/
        goToStage(1);
    }

    public static void goToStage(int num){
        appStage.close();
        switch(num){
            case 1:
                appStage.setScene(new LoginView().getPage());
                break;
            case 2:
                appStage.setScene(new FightView().getPage());
                break;
        }
        appStage.show();
    }
}
