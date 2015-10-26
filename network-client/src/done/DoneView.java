package done;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import waiting.WaitController;

public class DoneView {

    GridPane container = new GridPane();
    private Scene view = new Scene(container, 400, 100);

    public DoneView(String outcome) {
        /*Setting the view parameters*/
        for(int i = 0; i < 3; i++){
            container.getColumnConstraints().add(new ColumnConstraints(100));
        }
        container.setAlignment(Pos.TOP_CENTER);
        container.setHgap(10);
        container.setVgap(10);
        container.setPadding(new Insets(15, 15, 15, 15));

        Text lose = new Text("You " + outcome + "!");
        lose.setFont(new Font("Cambria", 32));

        /*Setting up the title pane*/
        container.add(lose, 0, 0, 3, 1);
    }

    public Scene getPage(){
        return view;
    }


}
