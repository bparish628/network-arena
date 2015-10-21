package common;

import javafx.scene.image.Image;

public class Job {
    private int hp;
    private int attack;
    private int defense;
    private String job;
    private Image image;
    private Action basic;
    private Action defend;
    private Action special;

    public Job(String job){
        if(job == "Melee"){
            setMelee();
        }
        if(job == "Magic"){
            setMagic();
        }
        if(job == "Range"){
            setRange();
        }
    }

    public Job(String job, int hp, int attack, int defense, Action basic, Action defend, Action special, String image){
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.job = job;
        this.basic = basic;
        this.defend = defend;
        this.special = special;
        this.image = new Image("/images/" + image + ".png", 50, 75, false, false);;
    }

    private void setMelee(){
        this.hp = 400;
        this.attack = 100;
        this.defense = 60;
        this.job = "Melee";
        this.basic = new Action("Attack", "Basic");
        this.defend = new Action("Defend", "Defend");
        this.special = new Action("Whirlwind", "Special");
        this.image = new Image("/images/melee.png", 50, 75, false, false);
    }

    private void setMagic(){
        this.hp = 300;
        this.attack = 140;
        this.defense = 50;
        this.job = "Magic";
        this.basic = new Action("Attack", "Basic");
        this.defend = new Action("Guard", "Defend");
        this.special = new Action("Firestorm", "Special");
        this.image = new Image("/images/magic.png", 50, 75, false, false);
    }

    private void setRange(){
        this.hp = 350;
        this.attack = 90;
        this.defense = 80;
        this.job = "Range";
        this.basic = new Action("Attack", "Basic");
        this.defend = new Action("Hide", "Defend");
        this.special = new Action("Multishot", "Special");
        this.image = new Image("/images/range.png", 50, 75, false, false);
    }

    public int getHp(){
        return hp;
    }

    public int getDefense(){
        return defense;
    }

    public int getAttack(){
        return attack;
    }

    public String getJob(){
        return job;
    }

    public Action getBasicAction(){
        return basic;
    }

    public Action getDefendAction(){
        return defend;
    }

    public Action getSpecialAction(){
        return special;
    }

    public Image getImage(){
        return image;
    }
}
