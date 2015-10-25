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
    public static Player[] updatePlayers(GameUpdate gu) {
        Player[] result = new Player[3];
        try {
            int j = 0;
            for(int i = 0; i<result.length; i++) {
                if(gu.getPlayer(i).equals(getUser())) {
                    j++;//j = i+1
                    i--;//i = i-1
                    continue; //i-1 incremented to i.  i preserved, j = i+1
                }
                result[i] = gu.getPlayer(j);
                j++;
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("OH NO from Controller.updatePlayers");
        return null;
    }

    public static void initSocket(){
        clientSocket = new SocketConnector();
    }

    public static void listenerActivate() {
        clientSocket.listen();
    }
}
