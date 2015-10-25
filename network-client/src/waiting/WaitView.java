package waiting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class WaitView{

    GridPane container = new GridPane();
    private Scene view = new Scene(container, 400, 100);
    private WaitController WaitCtrl = new WaitController();

    public WaitView() {
        /*Setting the view parameters*/
        for(int i = 0; i < 3; i++){
            container.getColumnConstraints().add(new ColumnConstraints(100));
        }
        container.setAlignment(Pos.TOP_CENTER);
        container.setHgap(10);
        container.setVgap(10);
        container.setPadding(new Insets(15, 15, 15, 15));

        /*Setting up the title pane*/
        container.add(WaitCtrl.getText(), 0, 0, 3, 1);
    }

    public Scene getPage(){
        return view;
    }


}
