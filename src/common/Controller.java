package common;

public class Controller {

    private static Player user;
    private static Player players[];

    public static Player getUser(){
        if(user == null){
            user = new Player();
        }
        return user;
    }

    public static Player[] getPlayers(){
        if(players == null){
            players = new Player[3];
            for(int i = 0; i < players.length; i++){
                players[i] = new Player();
                players[i].setUsername("Player " + i);
                players[i].setSelectedClass("Melee");
            }
        }
        return players;
    }
}
