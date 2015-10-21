package common;

public class Action {
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
