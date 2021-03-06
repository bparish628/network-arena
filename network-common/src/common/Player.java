package common;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;


public class Player implements Serializable{

    private String username;
    private Job selectedClass;
    private int playerNum;
    private boolean myTurn;

    /*Condition of character*/
    private int currentHP;
    private boolean guardStatus;
    private Action queuedAction;
    private Player target;

    public Player(){
        username = null;
        selectedClass = null;
        playerNum = 1;
        guardStatus = false;
    }

    public String getUsername(){
        return username;
    }

    public int getPlayerNum() {return playerNum;}

    public Job getSelectedClass(){
        return selectedClass;
    }

    public int getCurrentHP(){
        return currentHP;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setSelectedClass(String classString){
        this.selectedClass = new Job(classString);
        this.currentHP = this.selectedClass.getHp();
    }

    public void setPlayerNum(int num) {this.playerNum = num;}

    public void setCurrentHP(int hp){
        this.currentHP = hp;
    }

    public void setGuarding(boolean guard){
         this.guardStatus = guard;
    }

    public void setMyTurn(boolean turn){
        this.myTurn = turn;
    }

    public boolean isMyTurn(){
        return myTurn;
    }

    public boolean isAlive(){
        return this.currentHP > 0;
    }

    public boolean isGuarding(){
        return this.guardStatus;
    }

    public Player getTarget(){
        if(target == null){
            target = new Player();
        }
        return target;
    }

    public void setTarget(Player target){
        this.target = target;
    }

    public void setQueuedAction(Action action){
        this.queuedAction = action;}

    public Action getQueuedAction(){
        if(queuedAction == null){
            queuedAction = selectedClass.getBasicAction();
        }
        return queuedAction;
    }

    @Override
    public String toString() {
        return String.format("server.Player #%d: %s\n", this.playerNum, this.username);
    }

    @Override
    public boolean equals(Object o) {
        if((o instanceof Player) && (((Player)o).getPlayerNum() == this.playerNum)) {
            return true;
        }
        return false;
    }
}
