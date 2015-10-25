package common;

public class Combat {

    public static void attack(Player user, Player target){
        user.setGuarding(false);
        int damage = (user.getSelectedClass().getAttack() / target.getSelectedClass().getDefense()) * 80;
        if(weakness(user, target)){
            damage*=2;
        }
        applyDamage(target, damage);
    }

    public static void guard(Player user){
        user.setGuarding(true);
    }

    public static void special(Player user, Player players[]){
        user.setGuarding(false);
        for(int i = 0; i < players.length; i++){
            int damage = (user.getSelectedClass().getAttack() / players[i].getSelectedClass().getDefense()) * 40;
            applyDamage(players[i], damage);
        }

    }

    private static void applyDamage(Player target, int damage){
        if(target.isGuarding()){
            damage *= .25;
        }

        int newHp = target.getCurrentHP() - damage;
        target.setCurrentHP(newHp);
    }

    private static boolean weakness(Player user, Player target){
        String p1Job = user.getSelectedClass().getJob();
        String p2Job = target.getSelectedClass().getJob();
        return (p1Job == "Melee" && p2Job == "Magic") ||
                (p1Job == "Magic" && p2Job == "Range") ||
                (p1Job == "Range" && p2Job == "Melee");
    }
}