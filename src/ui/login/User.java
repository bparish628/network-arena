package ui.login;

import classes.Job;

public class User {

    private String username;
    private String hostname;
    private Job selectedClass;

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

    public void setUsername(String username){
        this.username = username;
    }

    public void setHostname(String hostname){
        this.hostname = hostname;
    }

    public void setSelectedClass(String classString){
        this.selectedClass = new Job(classString);
    }
}
