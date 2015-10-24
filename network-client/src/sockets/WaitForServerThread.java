package sockets;

import java.io.ObjectInputStream;

/**
 * Created by Kale on 10/24/2015.
 */
public class WaitForServerThread extends Thread implements Runnable {
    private Object msg;
    private String threadName;
    private Thread t;
    private ObjectInputStream in;
    private boolean done;


    public WaitForServerThread(String name, ObjectInputStream ois) {
        msg = null;
        threadName = name;
        t = null;
        in = ois;
        done = false;
    }

    public void run() {
        msg = null;
        try {
            while (msg == null) {
                msg = in.readObject();
                Thread.sleep(50);
            }
            done = true;
            throw new InterruptedException((String)msg);
        } catch(Exception e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        if (msg instanceof String) {
            //System.out.println((String) msg);
        } else {
            System.out.println("OH NOES THAT DOESN'T WORK");
            System.out.println(msg);
        }
    }

    public void start() {
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public boolean isComplete() {
        return done;
    }

    public Object getMessage() {
        return msg;
    }
}