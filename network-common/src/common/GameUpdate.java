package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Kale on 10/24/2015.
 */
public class GameUpdate implements Serializable{
    private Player[] plyrs;
    private Action whatHappened;
    private int targetPlayerNum;
    private int attackingPlayer;
    private int nextPlayer;

    public GameUpdate(Player[] p, Action wh, int targ, int att, int np) {
        plyrs = new Player[4];
        for(int i = 0; i < plyrs.length; i++) {
            plyrs[i] = p[i];
        }
        whatHappened = wh;
        targetPlayerNum = targ;
        attackingPlayer = att;
        nextPlayer = np;
    }

    public GameUpdate(){}

    public Player getPlayer(int index) {
        if(index >= 0 && index < plyrs.length) {
            if(plyrs[index] != null) {
                return plyrs[index];
            } else {
                return null; //should be an error or something
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("GameUpdate.getPlayer: index is out of range");
        }
    }

    public int getTargetPlayerNum() {return targetPlayerNum;}

    public int getAttackingPlayer() {return attackingPlayer;}

    public int getNextPlayer() {return nextPlayer;}

    @Override
    public String toString() {
        String ret = "Players:\n";
        for(Player p : plyrs) {
            ret += p.toString();
        }
        ret += String.format("%s attacked\n%s with\n%s\n",
                plyrs[targetPlayerNum-1],
                plyrs[attackingPlayer-1],
                whatHappened.getName());
        ret += String.format("It is now Player %d's turn.",nextPlayer);
        return ret;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        for(int i = 0; i < plyrs.length; i++) {
            plyrs[i] = (Player) in.readObject();
        }
        whatHappened = (Action) in.readObject();
        targetPlayerNum = in.readInt();
        attackingPlayer = in.readInt();
        nextPlayer = in.readInt();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        for(Player p : plyrs) {
            out.writeObject(p);
        }
        out.writeObject(whatHappened);
        out.writeInt(targetPlayerNum);
        out.writeInt(attackingPlayer);
        out.writeInt(nextPlayer);
    }
}
