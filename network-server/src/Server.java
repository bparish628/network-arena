/**
 * The main class for the server application.
 */
public class Server {

	private final static String hostName = "localhost";
	private final static int MAX_PLAYERS = 4;
	private final static int[] PORTS = {45561,45570,45580,45590};
	private static Player[] plyrs;
	private final String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|"; //used for telling the main class or client that an error occurred
	
	public static void main(String[] args) {
		plyrs = new Player[MAX_PLAYERS];
		ConnectionsManager cnnMgr = new ConnectionsManager();
		String response;
		try {
			for(int i = 0; i < MAX_PLAYERS; i++) {
				try {
				plyrs[i] = cnnMgr.addConnection(PORTS[i]);
				} catch(Exception e) {
					i--;
					System.out.println("CONNECTION #" + i + " FAILED, RETRYING");
					continue;
				}
				System.out.println(res);
				cnnMgr.sendMessage(String.format("%d", i+1), i+1);
				System.out.printf("PLAYER SUCCESSFULLY ADDED!\n%s", plyrs[i]);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("SUCCESS!");
		cnnMgr.broadcast("Hi there.");
	}

}
