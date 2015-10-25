package waiting;

import common.Controller;
import common.Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import run.App;

public class WaitController extends Controller{
    public WaitController(){

    }

    public Text getText(){
        Text waitingText = new Text("Waiting for more players");
        return waitingText;
    }
}
