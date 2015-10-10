package ui.fight;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ui.login.User;

public class FightController {
    /*User*/
    private User user;

    /*Changeable content*/
    ProgressBar hpBar;
    VBox logBox;
    Label currentHPText;
    Text stat1;
    Text stat2;
    Text stat3;
    Text stat4;

    public FightController(){
        user = new User();
        setUser();

        hpBar = new ProgressBar();
        logBox = new VBox();
        setupLabels();
    }

    public void setUser(){
        user.setUsername("Benji");
        user.setSelectedClass("Magic");
        user.setHostname("Hostname is Here");
    }

    private void setupLabels(){
        currentHPText = new Label();
        currentHPText.setPrefWidth(190);
        currentHPText.setAlignment(Pos.CENTER_RIGHT);

        stat1 = new Text();
        stat2 = new Text();
        stat3 = new Text();
        stat4 = new Text();
    }
    public HBox getActionMenu() {
        /*Set up the container*/
        HBox actionMenu = new HBox();
        actionMenu.setAlignment(Pos.CENTER);
        actionMenu.setPadding(new Insets(15, 12, 15, 12));
        actionMenu.setSpacing(10);
        actionMenu.setStyle("-fx-background-color: lightsteelblue");

        /*Setup action buttons*/
        Label actionsLabel = new Label("Actions: ");

        Button action1Button = new Button(user.getSelectedClass().getAction1());
        Button action2Button = new Button(user.getSelectedClass().getAction2());
        Button action3Button = new Button(user.getSelectedClass().getAction3());

        action1Button.setPrefSize(100, 20);
        action2Button.setPrefSize(100, 20);
        action3Button.setPrefSize(100, 20);

        actionMenu.getChildren().addAll(actionsLabel, action1Button, action2Button, action3Button);

        return actionMenu;
    }

    public VBox getUserInfoView() {
        VBox view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(8);
        view.setPrefWidth(200);
        view.setStyle("-fx-background-color: gainsboro");

        ImageView classImage = new ImageView();

        classImage.setImage(user.getSelectedClass().getImage());
        classImage.setFitWidth(190);

        Label hpLabel = new Label("HP");
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        hpBar.setPrefWidth(190);
        updateHPBar();

        Text StatsLabel = new Text("Stats:");
        StatsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        /*Creating stat boxes*/
        HBox statLine1 = new HBox();
        statLine1.setPadding(new Insets(0, 0, 0, 15));
        Label StatLabel1 = new Label("Stat1: ");
        stat1.setText("stat1");
        statLine1.getChildren().addAll(StatLabel1, stat1);

        HBox statLine2 = new HBox();
        statLine2.setPadding(new Insets(0,0,0,15));
        Label StatLabel2 = new Label("Stat2: ");
        stat2.setText("stat2");
        statLine2.getChildren().addAll(StatLabel2, stat2);

        HBox statLine3 = new HBox();
        statLine3.setPadding(new Insets(0,0,0,15));
        Label StatLabel3 = new Label("Stat3: ");
        stat3.setText("stat3");
        statLine3.getChildren().addAll(StatLabel3, stat3);

        HBox statLine4 = new HBox();
        statLine4.setPadding(new Insets(0,0,0,15));
        Label StatLabel4 = new Label("Stat4: ");
        stat4.setText("stat4");
        statLine4.getChildren().addAll(StatLabel4, stat4);

        view.getChildren().addAll(classImage, hpLabel, hpBar, currentHPText, StatsLabel, statLine1, statLine2, statLine3, statLine4);


        return view;
    }

    public VBox getLog(){
        /*Set up the log box*/
        logBox.setPadding(new Insets(15, 12, 15, 12));
        logBox.setSpacing(10);
        logBox.setPrefHeight(100);
        logBox.setStyle("-fx-background-color: snow; -fx-border-color: lightgray");

        for(int i = 1; i < 10; i++){
            updateLog("Testing the log. This is test #" + i);
        }

        return logBox;
    }

    public VBox getOpponents(){
        /*Set up the opponent boxes*/
        VBox opponentsBox = new VBox();
        opponentsBox.setPadding(new Insets(12, 12, 12, 12));
        opponentsBox.setSpacing(10);
        logBox.setPrefHeight(100);
        opponentsBox.setStyle("-fx-background-color: snow; -fx-border-color: lightgray");


        opponentsBox.getChildren().addAll(generateOpponentBox(), generateOpponentBox(), generateOpponentBox());
        return opponentsBox;
    }

    private HBox generateOpponentBox(){
        HBox oppBox = new HBox();
        oppBox.setPrefHeight(150);
        oppBox.setStyle("-fx-background-color: snow; -fx-border-color: black");

        return oppBox;
    }

    private void updateLog(String text){
        Text newLogItem = new Text(text);
        if(logBox.getChildren().size() > 4){
            logBox.getChildren().remove(0,1);
        }
        logBox.getChildren().add(newLogItem);
    }

    private void updateHPBar(){
        currentHPText.setText(Integer.toString(user.getCurrentHP()) + "/" + Integer.toString(user.getSelectedClass().getHp()));
        double hpPercent = (double)(user.getCurrentHP())/(double)(user.getSelectedClass().getHp());
        hpBar.setProgress(hpPercent);
        if(hpPercent > .3){
            hpBar.setStyle("-fx-accent: green");
        }else{
            hpBar.setStyle("-fx-accent: crimson");
        }
    }
}
