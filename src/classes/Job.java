package classes;

import javafx.scene.image.Image;

public class Job {
    private int hp;
    private String job;
    private Image image;
    private String action1;
    private String action2;
    private String action3;

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

    public Job(String job, int hp, String action1, String action2, String action3, String image){
        this.hp = hp;
        this.job = job;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
        this.image = new Image("/ui/images/"+ image + ".png", 50, 75, false, false);;
    }

    private void setMelee(){
        this.hp = 100;
        this.job = "Melee";
        this.action1 = "Power Strike";
        this.action2 = "Defend";
        this.action3 = "Roll";
        this.image = new Image("/ui/images/melee.png", 50, 75, false, false);
    }

    private void setMagic(){
        this.hp = 50;
        this.job = "Magic";
        this.action1 = "Fireball";
        this.action2 = "Frostbold";
        this.action3 = "Lightning";
        this.image = new Image("/ui/images/magic.png", 50, 75, false, false);
    }

    private void setRange(){
        this.hp = 70;
        this.job = "Range";
        this.action1 = "Shoot";
        this.action2 = "Multishot";
        this.action3 = "Steal";
        this.image = new Image("/ui/images/range.png", 50, 75, false, false);
    }

    public int getHp(){
        return hp;
    }

    public String getJob(){
        return job;
    }

    public String getAction1(){
        return action1;
    }

    public String getAction2(){
        return action2;
    }

    public String getAction3(){
        return action3;
    }

    public Image getImage(){
        return image;
    }
}
