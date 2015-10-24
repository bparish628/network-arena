package common;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;


public class Player implements Serializable{

    private String username;
    private Job selectedClass;

    /*Condition of character*/
    private int currentHP;
    private boolean guardStatus;
    private Action queuedAction;
    private Player target;

    public Player(){
        username = null;
        selectedClass = null;
        guardStatus = false;
    }

    public String getUsername(){
        return username;
    }


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

    public void setCurrentHP(int hp){
        this.currentHP = hp;
    }

    public void setGuarding(boolean guard){
         this.guardStatus = guard;
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

    public void setQueuedAction(Action action){ this.queuedAction = action;}

    public Action getQueuedAction(){ return queuedAction;}

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        username = (String) in.readObject();
        selectedClass = (Job) in.readObject();
        currentHP = in.readInt();
        guardStatus = in.readBoolean();
        queuedAction = (Action) in.readObject();
        target = (Player) in.readObject();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(username);
        out.writeObject(selectedClass);
        out.writeInt(currentHP);
        out.writeBoolean(guardStatus);
        out.writeObject(queuedAction);
        out.writeObject(target);
    }
}
