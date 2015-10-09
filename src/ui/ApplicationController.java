package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.login.LoginView;

public class ApplicationController extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage start) {
        /*Set Application Title*/
        start.setTitle("The Network Arena");

        /*Set the Login page*/
        start.setScene(new LoginView().getPage());
        start.show();
    }
}
