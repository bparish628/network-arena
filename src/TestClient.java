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
		try {
			System.out.println("Attempting connection...");
			sendOut.println("ACK");
			String rawResp = readIn.readLine();
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
			sendOut.println("I'll be there for youuuuu");
		} catch(Exception e) {
			System.out.printf("Error transferring: %s", e.getMessage());
			System.out.println("Exiting.");
			System.exit(0);
		}
		System.out.println("SUCCESSFUL CONNECTION!");
		System.out.println("Waiting for server greeting...");
		try {
			System.out.println(readIn.readLine());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//persist until user dc's
		Scanner in = new Scanner(System.in);
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
