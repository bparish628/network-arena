package sockets;

import common.Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class SocketConnector extends Controller{

    private final static int CONN_PORT = 45560;
    private final static String HOST_NAME = "localhost";
    private final static String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|";
    private static Socket myConn;
    private static ObjectOutputStream sendOut;
    private static ObjectInputStream readIn;
    private String rawResp;

    public SocketConnector() {
        //make the initial connection and get new port num from server
        try {
            myConn = new Socket(InetAddress.getByName(null), CONN_PORT);
            sendOut = new ObjectOutputStream(myConn.getOutputStream());
            readIn = new ObjectInputStream(myConn.getInputStream());

        } catch (Exception e) {
            System.out.printf("Error connecting: %s\n", e.getMessage());
            System.out.println("Exiting.");
            System.exit(0);
        }

        initConnection();
    }

    public String waitForServer() {
        String serverMsg = null;
        WaitForServerThread wfst = new WaitForServerThread("w", readIn);
        wfst.start();
        while(!wfst.isComplete()) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ie) {
                break;
            }
        }
        serverMsg = (String)wfst.getMessage();
        System.out.print(serverMsg);
        return serverMsg;
    }

    public void initConnection(){
        int newPort;
        //try to connect to new port
        try {
            System.out.println("Attempting connection...");
            sendOut.writeObject("ARENA");
            newPort = readIn.readInt();
            System.out.printf("Response from server received! %d", newPort);
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
        } catch(Exception e) {
            System.out.printf("Error transferring: %s\n", e.getMessage());
            System.out.println("Exiting.");
            System.exit(0);
        }
    }

    public void send(String line){
//        sendOut.writeObject(line);
    }

    public void close(){
        try {
            sendOut.close();
            readIn.close();
            myConn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
//
//    public static void main(String[] args) {
//
//
//        Scanner in = new Scanner(System.in);
//        String s;
//        try {
//            rawResp = readIn.readLine();
//            //this will be the player number
//            System.out.println(rawResp);
//            System.out.println("YOU ARE PLAYER #" + rawResp);
//            System.out.print("Enter username: ");
//            s = in.nextLine();
//            plyr = new Player(s, Integer.parseInt(rawResp));
//
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.printf("You are %s", plyr);
//            System.out.println("Waiting for more players...");
//            rawResp = readIn.readLine();
//            System.out.printf("server.Server greeting: %s\n", rawResp);
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
//        //persist until user dc's
//        System.out.println("k to disconnect");
//        while(!in.next().equals("k")) {
//            System.out.println("k to disconnect");
//        }
//
//    }

}

