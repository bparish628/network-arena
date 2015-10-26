package common;

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
        for(int i = 0; i<players.length; i++){
            System.out.println(players[i].getUsername());
        }
    }

    public static void initSocket(){
        clientSocket = new SocketConnector();
    }

    public static void listenerActivate() {
        clientSocket.listen();
    }
}
