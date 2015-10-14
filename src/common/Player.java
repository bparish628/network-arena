package common;

import common.Job;

public class Player {

    private String username;
    private Job selectedClass;

    /*Condition of character*/
    private int currentHP;

    public Player(){
        username = null;
        selectedClass = null;
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
}
