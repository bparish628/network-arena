
/**
 * The main class for the server application.
 */
public class Server {

	private final static String hostName = "localhost";
	private final static int[] PORTS = {45561,45570,45580,45590};
	
	public static void main(String[] args) {
		ConnectionsManager cnnMgr = new ConnectionsManager(PORTS);
		System.out.println("SUCCESS!");
		cnnMgr.broadcast("Hi there.");
	}

}
