package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.fight.FightView;
import ui.login.LoginView;

public class ApplicationController extends Application{

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

    public void goToStage(int num){
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
