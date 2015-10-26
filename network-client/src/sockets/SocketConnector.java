package sockets;

import common.Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class SocketConnector extends Controller{

    private final static int CONN_PORT = 55560;
    private final static String HOST_NAME = "localhost";
    private final static String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|";
    private static Socket myConn;
    private static ObjectOutputStream sendOut;
    private static ObjectInputStream readIn;
    private ServerListener serverListener;

    public SocketConnector() {
        //make the initial connection and get new port num from server

        try {
            myConn = new Socket(InetAddress.getByName(null), CONN_PORT);
            sendOut = new ObjectOutputStream(myConn.getOutputStream());
            readIn = new ObjectInputStream(myConn.getInputStream());
            serverListener = new ServerListener();
        } catch (Exception e) {
            System.out.printf("Error connecting: %s\n", e.getMessage());
            System.out.println("Exiting.");
            System.exit(0);
        }

        initConnection();
    }

    public void initConnection(){
        int newPort;

        //try to connect to new port
        try {
            System.out.println("Attempting connection...");
            sendOut.writeObject("ARENA");
            newPort = readIn.readInt();
            System.out.printf("Response from server received! %d\n", newPort);
            sendOut.flush();
            sendOut.close();
            readIn.close();
            myConn.close();
            myConn = new Socket(InetAddress.getByName(null), newPort);
            sendOut = new ObjectOutputStream(myConn.getOutputStream());
            readIn = new ObjectInputStream(myConn.getInputStream());
            sendOut.writeObject(getUser());
            System.out.println("SUCCESSFUL CONNECTION!");
            System.out.println("Waiting for server...");
            listen();
        } catch(Exception e) {
            System.out.printf("Error transferring: %s\n", e.getMessage());
            System.out.println("Exiting.");
            System.exit(0);
        }
    }

    public void listen() {
        serverListener.listen(readIn);
    }

    public static void send(Object obj){
        try {
            sendOut.writeObject(obj);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void close(){
        try {
            sendOut.close();
            readIn.close();
            myConn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

}

