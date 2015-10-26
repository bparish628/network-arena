package common;

import fight.FightController;
import run.App;
import sockets.SocketConnector;

public class Controller {

    private static Player user;
    private static Player players[];
    protected static SocketConnector clientSocket;

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

    public static void updatePlayers(Player[] p) {
        Player[] result = new Player[3];
        int j = 0;
        for(int i = 0; i<p.length; i++) {
            if(p[i].equals(getUser())) {
                user = p[i];
                j = 1;
            }else{
                result[i-j] = p[i];
            }
        }
        players = result;
    }

    public static Player findPlayer(int number) {
        Player p = new Player();
        for(int i = 0; i < players.length; i++){
            if(players[i].getPlayerNum() == number){
                p = players[i];
                break;
            }
            if(user.getPlayerNum() == number){
                p = user;
                break;
            }
        }
        return p;
    }


    public static void initSocket(){
        clientSocket = new SocketConnector();
    }

    public static void listenerActivate() {
        clientSocket.listen();
    }
}
