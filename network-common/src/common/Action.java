package common;

import java.io.Serializable;

public class Action implements Serializable{
    private String name;
    private String type;

    public Action(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }
}
