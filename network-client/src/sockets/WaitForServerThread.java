package sockets;

import common.ServerRequest;

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
            msg = in.readObject();
            while (msg == null) {
                Thread.currentThread().sleep(50);
                msg = in.readObject();
            }
            done = true;
            notify();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if (msg instanceof String) {
            //System.out.println((String) msg);
        } else if(msg instanceof ServerRequest) {
            System.out.printf("Game update:\n%s\n", ((ServerRequest)msg).toString());
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
