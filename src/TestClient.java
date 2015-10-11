import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class TestClient {

	private final static int CONN_PORT = 45560;
	private final static String HOST_NAME = "localhost";
	private final static String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|";
	private static Socket myConn;
	private static PrintWriter sendOut;
	private static BufferedReader readIn;
	private static Player plyr;
	
	public static void main(String[] args) {
		//make the initial connection and get new port num from server
		try {
			myConn = new Socket(InetAddress.getByName(null), CONN_PORT);
			sendOut = new PrintWriter(myConn.getOutputStream(), true);
			readIn = new BufferedReader(
						new InputStreamReader(myConn.getInputStream()));
		} catch(Exception e) {
			System.out.printf("Error connecting: %s\n",e.getMessage());
			System.out.println("Exiting.");
			System.exit(0);
		}
		//try to connect to new port
		String rawResp;
		try {
			System.out.println("Attempting connection...");
			sendOut.println("ACK");
			rawResp = readIn.readLine();
			System.out.println("Response from server received!");
			int newPort = Integer.parseInt(rawResp);
			sendOut.flush();
			sendOut.close();
			readIn.close();
			myConn.close();
			myConn = new Socket(InetAddress.getByName(null), newPort);
			sendOut = new PrintWriter(myConn.getOutputStream(), true);
			readIn = new BufferedReader(
						new InputStreamReader(myConn.getInputStream()));
			sendOut.println("Requesting connection");
			System.out.println("SUCCESSFUL CONNECTION!");
			System.out.println("Waiting for server...");
		} catch(Exception e) {
			System.out.printf("Error transferring: %s\n", e.getMessage());
			System.out.println("Exiting.");
			System.exit(0);
		}
		
		Scanner in = new Scanner(System.in);
		String s;
		try {
			rawResp = readIn.readLine();
			//this will be the player number
			System.out.println(rawResp);
			System.out.println("YOU ARE PLAYER #" + rawResp);
			System.out.print("Enter username: ");
			s = in.nextLine();
			plyr = new Player(s, Integer.parseInt(rawResp));
			sendOut.println(s);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			System.out.printf("You are %s", plyr);
			System.out.println("Waiting for more players...");
			rawResp = readIn.readLine();
			System.out.printf("Server greeting: %s\n", rawResp);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//persist until user dc's
		System.out.println("k to disconnect");
		while(!in.next().equals("k")) {
			System.out.println("k to disconnect");
		}
		
		try {
			in.close();
			sendOut.close();
			readIn.close();
			myConn.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
