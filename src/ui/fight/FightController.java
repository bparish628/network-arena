package ui.fight;

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
import common.Player;

public class FightController {
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
    Text stat4;

    public FightController(){
        /*Set up user*/
        user = new Player();
        setPlayer(user, "Benji", "Melee");

        /*Set up other players*/
        players = new Player[3];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player();
        }

        setPlayer(players[0], "Kale", "Magic");
        setPlayer(players[1], "John", "Range");
        setPlayer(players[2], "Jackson", "Melee");

        userHpBar = new ProgressBar();
        player2HpBar = new ProgressBar();
        player3HpBar = new ProgressBar();
        player4HpBar = new ProgressBar();

        logBox = new VBox();
        setupLabels();
    }

    public void setPlayer(Player player, String username, String job){
        player.setUsername(username);
        player.setSelectedClass(job);
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

        /*Class Image*/
        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(15,0,0,0));
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
        statLine2.setPadding(new Insets(0,0,0,15));
        Label StatLabel2 = new Label("Attack: ");
        stat2.setText(Integer.toString(user.getSelectedClass().getAttack()));
        statLine2.getChildren().addAll(StatLabel2, stat2);

        HBox statLine3 = new HBox();
        statLine3.setPadding(new Insets(0,0,0,15));
        Label StatLabel3 = new Label("Defense: ");
        stat3.setText(Integer.toString(user.getSelectedClass().getDefense()));
        statLine3.getChildren().addAll(StatLabel3, stat3);

//        HBox statLine4 = new HBox();
//        statLine4.setPadding(new Insets(0,0,0,15));
//        Label StatLabel4 = new Label("Guard Enabled: ");
//        stat4.setText("False");
//        statLine4.getChildren().addAll(StatLabel4, stat4);

        view.getChildren().addAll(hpLabel, userHpBar, currentHPText, StatsLabel, statLine1, statLine2, statLine3, imageBox);


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

        opponentsBox.getChildren().addAll(generateOpponentBox(2, players[0]), generateOpponentBox(3, players[1]), generateOpponentBox(4, players[2]));
        return opponentsBox;
    }

    private HBox generateOpponentBox(int playerNum, Player player){
        HBox oppBox = new HBox();
        oppBox.setPadding(new Insets(12, 12, 12, 12));
        oppBox.setPrefHeight(85);
        oppBox.setAlignment(Pos.CENTER_LEFT);
        oppBox.setStyle("-fx-background-color: gainsboro; -fx-border-color: black");

        /*Image*/
        ImageView classImage = new ImageView();
        classImage.setImage(player.getSelectedClass().getImage());

        /*Username*/


        /*Hp bar*/
        VBox hpBox = new VBox();
        hpBox.setAlignment(Pos.CENTER);
        hpBox.setPadding(new Insets(0,0,0,65));
        Text username = new Text(player.getUsername());
        username.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        switch(playerNum){
            case 2:
                updateProgressBar(player2HpBar, player);
                hpBox.getChildren().addAll(username, player2HpBar);
                break;
            case 3:
                updateProgressBar(player3HpBar, player);
                hpBox.getChildren().addAll(username, player3HpBar);
                break;
            case 4:
                updateProgressBar(player4HpBar, player);
                hpBox.getChildren().addAll(username, player4HpBar);
                break;
        }

        oppBox.getChildren().addAll(classImage, hpBox);
        return oppBox;
    }

    private void updateLog(String text){
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
