package ui.fight;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FightView {
    BorderPane container = new BorderPane();
    private Scene view = new Scene(container, 700, 700);
    private FightController FightCtrl = new FightController();

    public FightView() {
        /*Setting the view parameters*/
        container.setTop(FightCtrl.getActionMenu());
        container.setLeft(FightCtrl.getUserInfoView());
        container.setCenter(FightCtrl.getOpponents());
        container.setBottom(FightCtrl.getLog());
    }

    public Scene getPage(){
        return view;
    }
}
