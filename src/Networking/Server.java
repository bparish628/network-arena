package Networking;

/**
 * The main class for the server application.
 */
public class Server {

	private final static String hostName = "localhost";
	private final static int MAX_PLAYERS = 4;
	private final static int[] PORTS = {45561,45570,45580,45590};
	private static Player[] plyrs;
	
	public static void main(String[] args) {
		plyrs = new Player[MAX_PLAYERS];
		ConnectionsManager cnnMgr = new ConnectionsManager();
		String response;
		try {
			for(int i = 0; i < MAX_PLAYERS; i++) {
				System.out.println(cnnMgr.addConnection(PORTS[i]));
				cnnMgr.sendMessage(String.format("%d", i+1), i+1);
				response = cnnMgr.waitForResponse(i+1);
				plyrs[i] = new Player(response, i+1);
				System.out.printf("PLAYER SUCCESSFULLY ADDED!\n%s", plyrs[i]);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("SUCCESS!");
		cnnMgr.broadcast("Hi there.");
	}

}
