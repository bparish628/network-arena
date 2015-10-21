package fight;

import events.EventsController;
import common.Controller;
import common.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FightController extends Controller{
    /*User*/
    private Player user;

    /*Opponent*/
    private Player players[];

    /*Changeable content*/
    ProgressBar userHpBar;
    ProgressBar player2HpBar;
    ProgressBar player3HpBar;
    ProgressBar player4HpBar;
    VBox logBox;
    Label currentHPText;
    Text stat1;
    Text stat2;
    Text stat3;
    Text target;

    public FightController(){
        /*get user*/
        user = getUser();

        /*get other players*/
        players = getPlayers();

        userHpBar = new ProgressBar();
        player2HpBar = new ProgressBar();
        player3HpBar = new ProgressBar();
        player4HpBar = new ProgressBar();

        logBox = new VBox();
        setupLabels();
    }

    private void setupLabels(){
        currentHPText = new Label();
        currentHPText.setPrefWidth(190);
        currentHPText.setAlignment(Pos.CENTER_RIGHT);

        stat1 = new Text();
        stat2 = new Text();
        stat3 = new Text();
        target = new Text();
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

        Button attackButton = new Button(user.getSelectedClass().getBasicAction().getName());
        Button defendButton = new Button(user.getSelectedClass().getDefendAction().getName());
        Button specialButton = new Button(user.getSelectedClass().getSpecialAction().getName());

        attackButton.setPrefSize(100, 20);
        defendButton.setPrefSize(100, 20);
        specialButton.setPrefSize(100, 20);

        EventsController.attackButton(this, attackButton);
        EventsController.defendButton(this, defendButton);
        EventsController.specialButton(this, specialButton);

        actionMenu.getChildren().addAll(actionsLabel, attackButton, defendButton, specialButton);

        return actionMenu;
    }

    public VBox getUserInfoView() {
        VBox view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(8);
        view.setPrefWidth(200);
        view.setStyle("-fx-background-color: gainsboro");

        /*Class Image*/
        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(15, 0, 0, 0));
        ImageView classImage = new ImageView();
        classImage.setImage(user.getSelectedClass().getImage());
        imageBox.getChildren().add(classImage);

        Label hpLabel = new Label("HP");
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        userHpBar.setPrefWidth(190);
        updateUserHPBar();

        Text StatsLabel = new Text("Stats:");
        StatsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        /*Creating stat boxes*/
        HBox statLine1 = new HBox();
        statLine1.setPadding(new Insets(0, 0, 0, 15));
        Label StatLabel1 = new Label("Username: ");
        stat1.setText(user.getUsername());
        statLine1.getChildren().addAll(StatLabel1, stat1);

        HBox statLine2 = new HBox();
        statLine2.setPadding(new Insets(0, 0, 0, 15));
        Label StatLabel2 = new Label("Attack: ");
        stat2.setText(Integer.toString(user.getSelectedClass().getAttack()));
        statLine2.getChildren().addAll(StatLabel2, stat2);

        HBox statLine3 = new HBox();
        statLine3.setPadding(new Insets(0, 0, 0, 15));
        Label StatLabel3 = new Label("Defense: ");
        stat3.setText(Integer.toString(user.getSelectedClass().getDefense()));
        statLine3.getChildren().addAll(StatLabel3, stat3);

        view.getChildren().addAll(hpLabel, userHpBar, currentHPText, StatsLabel, statLine1, statLine2, statLine3, imageBox);


        return view;
    }

    public VBox getLog(){
        /*Set up the log box*/
        logBox.setPadding(new Insets(15, 12, 15, 12));
        logBox.setSpacing(10);
        logBox.setPrefHeight(100);
        logBox.setStyle("-fx-background-color: snow; -fx-border-color: lightgray");
        updateLog("Welcome to the Network Arena");
        updateLog("Where you come to get connected!");

        return logBox;
    }

    public VBox getOpponents(){
        /*Set up the opponent boxes*/
        VBox opponentsBox = new VBox();
        opponentsBox.setPadding(new Insets(12, 12, 12, 12));
        opponentsBox.setSpacing(10);
        logBox.setPrefHeight(100);
        opponentsBox.setStyle("-fx-background-color: snow; -fx-border-color: lightgray");

        opponentsBox.getChildren().addAll(generateOpponentBox(0, players[0]), generateOpponentBox(1, players[1]), generateOpponentBox(2, players[2]));
        EventsController.targetSelector(this, opponentsBox);
        return opponentsBox;
    }

    private HBox generateOpponentBox(int playerNum, Player player){
        HBox oppBox = new HBox();
        oppBox.setPadding(new Insets(12, 12, 12, 12));
        oppBox.setPrefHeight(85);
        oppBox.setAlignment(Pos.CENTER_LEFT);
        oppBox.setStyle("-fx-background-color: gainsboro; -fx-border-color: black;");

        /*Image*/
        ImageView classImage = new ImageView();
        classImage.setImage(player.getSelectedClass().getImage());

        /*Hp bar*/
        VBox hpBox = new VBox();
        hpBox.setAlignment(Pos.CENTER);
        hpBox.setPadding(new Insets(0,0,0,65));
        Text username = new Text(player.getUsername());
        username.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        switch(playerNum){
            case 0:
                updateProgressBar(player2HpBar, player);
                hpBox.getChildren().addAll(username, player2HpBar);
                break;
            case 1:
                updateProgressBar(player3HpBar, player);
                hpBox.getChildren().addAll(username, player3HpBar);
                break;
            case 2:
                updateProgressBar(player4HpBar, player);
                hpBox.getChildren().addAll(username, player4HpBar);
                break;
        }

//        oppBox.addEventHandler();

        oppBox.getChildren().addAll(classImage, hpBox);
        return oppBox;
    }

    public void updateLog(String text){
        Text newLogItem = new Text(text);
        if(logBox.getChildren().size() > 4){
            logBox.getChildren().remove(0,1);
        }
        logBox.getChildren().add(newLogItem);
    }

    private void updateUserHPBar(){
        currentHPText.setText(Integer.toString(user.getCurrentHP()) + "/" + Integer.toString(user.getSelectedClass().getHp()));
        updateProgressBar(userHpBar, user);
    }

    private void updateProgressBar(ProgressBar bar, Player player){
        double hpPercent = (double)(player.getCurrentHP())/(double)(player.getSelectedClass().getHp());
        bar.setProgress(hpPercent);
        if(hpPercent > .3){
            bar.setStyle("-fx-accent: green");
        }else{
            bar.setStyle("-fx-accent: crimson");
        }
    }
}
