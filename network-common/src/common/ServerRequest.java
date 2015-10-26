package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ServerRequest implements Serializable{
    private Action selectedAction;
    private int targetNum;
    private int attackerNum;

    public ServerRequest(Action action, int target, int attacker) {
        selectedAction = action;
        targetNum = target;
        attackerNum = attacker;
    }


    public Action getSelectedAction() {return selectedAction;}

    public int getTargetNum() {return targetNum;}

    public int getAttackerNum() {return attackerNum;}
}
