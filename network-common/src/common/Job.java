package common;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class Job implements Serializable{
    private int hp;
    private int attack;
    private int defense;
    private String job;
    private transient Image image;
    private String imageURL;
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
        this.imageURL = "/images/" + image + ".png";
        this.image = new Image("/images/" + image + ".png", 50, 75, false, false);
    }

    private void setMelee(){
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        this.hp = rnd.nextInt(50) + 400;
        this.attack = rnd.nextInt(20) + 100;
        this.defense = rnd.nextInt(10) + 60;
        this.job = "Melee";
        this.basic = new Action("Attack", "Basic");
        this.defend = new Action("Defend", "Defend");
        this.special = new Action("Whirlwind", "Special");
        this.imageURL = "/images/melee.png";
        this.image = new Image("/images/melee.png", 50, 75, false, false);
    }

    private void setMagic(){
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        this.hp = rnd.nextInt(50) + 300;
        this.attack = rnd.nextInt(20) + 140;
        this.defense = rnd.nextInt(10) + 50;
        this.job = "Magic";
        this.basic = new Action("Attack", "Basic");
        this.defend = new Action("Guard", "Defend");
        this.special = new Action("Firestorm", "Special");
        this.imageURL = "/images/magic.png";
        this.image = new Image("/images/magic.png", 50, 75, false, false);
    }

    private void setRange(){
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        this.hp = rnd.nextInt(50) + 350;
        this.attack = rnd.nextInt(20) + 90;
        this.defense = rnd.nextInt(10) + 80;
        this.job = "Range";
        this.basic = new Action("Attack", "Basic");
        this.defend = new Action("Hide", "Defend");
        this.special = new Action("Multishot", "Special");
        this.imageURL = "/images/range.png";
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
        if(image == null){
            return new Image(imageURL, 50, 75, false, false);
        }
        return image;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        hp = in.readInt();
        attack = in.readInt();
        defense = in.readInt();
        imageURL = (String) in.readObject();
        job = (String) in.readObject();
        basic = (Action) in.readObject();
        defend = (Action) in.readObject();
        special = (Action) in.readObject();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(hp);
        out.writeInt(attack);
        out.writeInt(defense);
        out.writeObject(job);
        out.writeObject(imageURL);
        out.writeObject(basic);
        out.writeObject(defend);
        out.writeObject(special);
    }
}
