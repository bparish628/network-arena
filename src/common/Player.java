package common;

import common.Job;

public class Player {

    private String username;
    private Job selectedClass;

    /*Condition of character*/
    private int currentHP;
    private boolean guardStatus;
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
}
