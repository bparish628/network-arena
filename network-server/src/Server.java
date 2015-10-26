import common.*;

/**
 * The main class for the server application.
 */
public class Server {

	private final static String hostName = "localhost";
	private final static int MAX_PLAYERS = 4;
	private final static int[] PORTS = {55561,55570,55580,55590};
	private static Player[] plyrs;
	private final String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|"; //used for telling the main class or client that an error occurred
	
	public static void main(String[] args) {
		plyrs = new Player[MAX_PLAYERS];
		ConnectionsManager cnnMgr = new ConnectionsManager();
		try {
			for(int i = 0; i < MAX_PLAYERS; i++) {
				try {
				plyrs[i] = cnnMgr.addConnection(PORTS[i]);
				} catch(Exception e) {
					i--;
					System.out.println("CONNECTION #" + i + " FAILED, RETRYING");
					continue;
				}
				System.out.printf("PLAYER SUCCESSFULLY ADDED!\n%s", plyrs[i]);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("SUCCESS!");
		cnnMgr.broadcast("Hi there.");
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ie) {
			System.out.println("Interrupted.");
		}

		System.out.println("Done waiting, sending update.");
		cnnMgr.broadcast(plyrs);
		System.out.println("Done. Exiting.");

		cnnMgr.startBattle(plyrs);
	}

}
