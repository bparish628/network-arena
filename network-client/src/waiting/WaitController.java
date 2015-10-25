package waiting;

import common.Controller;
import javafx.scene.text.Text;

public class WaitController extends Controller{
    public WaitController(){

    }

    public Text getText(){
        Text waitingText = new Text("Waiting for more players");
        return waitingText;
    }
}
