package ui.login;

import classes.Job;

public class User {

    private String username;
    private String hostname;
    private Job selectedClass;

    /*Condition of character*/
    private int currentHP;

    public User(){
        username = null;
        hostname = null;
        selectedClass = null;
    }

    public String getUsername(){
        return username;
    }

    public String getHostname(){
        return hostname;
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

    public void setHostname(String hostname){
        this.hostname = hostname;
    }

    public void setSelectedClass(String classString){
        this.selectedClass = new Job(classString);
        this.currentHP = this.selectedClass.getHp();
    }

    public void setCurrentHP(int hp){
        this.currentHP = hp;
    }
}
