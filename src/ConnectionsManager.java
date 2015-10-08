import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Timer;

/**
 * This class is designed to make interacting with the four sockets easier.
 * @author Kale
 */
public class ConnectionsManager {
	
	//constants
	private final int MAX_CONNS = 4; //max PLAYER conns, 0 is default
	private final int DEFAULT_PORT = 45560;
	private final String CLOSE_MESSAGE = "CLOSE";
	private final String hostName = "localhost";
	//private arrays
	private Socket[] plyrConns;
	private BufferedReader[] pConReads;
	private PrintWriter[] pConOuts;
	//private fields
	private byte numConnections;
	private boolean readyForPlay;
	
	//constructors
	/**
	 * Default constructor, used if connections are expected to be added to ports determined after object creation.
	 */
	public ConnectionsManager() {
		//init all player connections, plus one for default ([0])
		plyrConns = new Socket[MAX_CONNS+1];
		pConReads = new BufferedReader[MAX_CONNS+1];
		pConOuts = new PrintWriter[MAX_CONNS+1];
		numConnections = 0;
		readyForPlay = false;
	}
	/**
	 * Full constructor, used if the ports are known when the object is created.
	 * @param host The host name for the connections.  This should be the same for all connections.
	 * @param portNums Array containing the port numbers that the clients will connect to.
	 * 
	 * Note: This may not be necessary/useful.  For right now, I'm using this as a place to store the connection process.
	 */
	public ConnectionsManager(int[] portNums) {
		//if less than 4 port numbers were passed in, then not ready.
		if(portNums.length < 4) {
			readyForPlay = false;
		}
		numConnections = 0;
		//try to connect the sockets.
		plyrConns = new Socket[MAX_CONNS+1];
		pConReads = new BufferedReader[MAX_CONNS+1];
		pConOuts = new PrintWriter[MAX_CONNS+1];
		ServerSocket ss;
		for(int i = 0; i < (portNums.length < 4 ? portNums.length : 4); i++) {
			try {
				System.out.printf("Waiting for connection #%d on port %d\n",i+1,DEFAULT_PORT);
				ss = new ServerSocket(DEFAULT_PORT);
				plyrConns[0] = ss.accept();
				pConOuts[0] = new PrintWriter(plyrConns[0].getOutputStream(), true);
				pConReads[0] = new BufferedReader(new InputStreamReader(plyrConns[0].getInputStream()));
				ss.close();
				String tmpResp = pConReads[0].readLine();
				System.out.println("Got something!");
				pConOuts[0].println(portNums[i]);
				pConOuts[0].close();
				pConReads[0].close();
				plyrConns[0].close();
				if(tmpResp.equals("ACK")) {
					System.out.println("It's an ACK!");
					ss = new ServerSocket(portNums[i]);
					plyrConns[i+1] = ss.accept();
					System.out.printf("ACCEPT #%d!\n",i+1);
					pConReads[i+1] = new BufferedReader(new InputStreamReader(plyrConns[i+1].getInputStream()));
					pConOuts[i+1] = new PrintWriter(plyrConns[i+1].getOutputStream(),true);
					System.out.println("Waiting for reconnect...");
					tmpResp = pConReads[i+1].readLine();
					System.out.printf("RECONNECTION! MESSAGE: %s\n", tmpResp);
					numConnections++;
					System.out.printf("Socket #%d successfully connected!\n",i+1);
				} else {
					Exception a = new Exception(
							String.format("Something weird happened: \"%s\" was received, not \"ACK\"!",tmpResp));
					throw a;
				}
			} catch(Exception e) {
				System.out.println("An error occurred while opening socket.");
				System.out.println("---Socket attempt information---");
				System.out.printf("Socket #%d: port: %d\n",i+1,portNums[i]);
				System.out.println(e.getMessage());
				break;
			}
		}
		//after that, check if they all connected.
		if(numConnections < 4) {
			System.out.println("ConnectionsManager is still waiting for more connections.");
		} else {
			readyForPlay = true;
		}
	}
	
	//getters
	/**
	 * Tests if the connections are ready for the game to start.
	 * @return True if the connections are ready for game start.
	 */
	public boolean isReady() {
		return readyForPlay;
	}
	/**
	 * Gets the number of connected clients.
	 * @return The number of connections.
	 */
	public byte numberOfConnections() {
		return numConnections;
	}
	/**
	 * Gets the host name used by the connections.
	 * @return The host name being used by the connections.
	 */
	public String getHost() {
		return hostName;
	}

	//setters and add methods
	/**
	 * Adds a connection with the current host name.
	 * @param port The port number to connect to.
	 * @throws Exception Thrown if the number of connections is already at maximum or if the connection fails.
	 */
	public void addConnection(int port) throws Exception {
		if(numConnections < MAX_CONNS) {
			try {
				ServerSocket ss = new ServerSocket(port);
				System.out.printf("Waiting for connection #%d on port %d\n",numConnections+1,port);
				plyrConns[numConnections] = ss.accept();
				ss.close();
				pConReads[numConnections] = new BufferedReader(new InputStreamReader(plyrConns[numConnections].getInputStream()));
				pConOuts[numConnections] = new PrintWriter(plyrConns[numConnections].getOutputStream(),true);
				System.out.printf("Socket #%d successfully connected!",++numConnections);
				if(numConnections == 4) {
					readyForPlay = true;
				}
			} catch(Exception e) {
				System.out.println("An error occurred while opening socket.");
				System.out.println("---Socket attempt information---");
				System.out.printf("Socket #%d: host: %s\tport: %d\n",numConnections+1,InetAddress.getByName(null),port);
				System.out.println(e.getMessage());
				throw new Exception(e);
			}
		} else {
			throw new Exception(String.format("ConnectionsManager.addConnection: Attempted to add more connections than allowed.  Max connections: %d\n",MAX_CONNS));
		}
	}
	
	//actions
	/**
	 * Removes the connection at the given index.
	 * @param index The zero based index at which the connection will be removed.
	 * @return True if removal was successful.
	 * @throws ArrayIndexOutOfBoundsException Thrown if index is negative or exceeds the number of current connections.
	 */
	public boolean removeConnection(int index) throws ArrayIndexOutOfBoundsException {
		if(index < numConnections && index >= 0) {
			if(plyrConns[index] == null) {
				System.out.println("DEBUG: Something is wrong.  plyrConns[index] is null but index < numConnections.");
				return false;
			} else {
				try {
					sendMessage(CLOSE_MESSAGE,index);
					plyrConns[index].close();
					plyrConns[index] = null;
					numConnections--;
					pConOuts[index] = null;
					pConReads[index] = null;
					//shift connections left if needed
					if(index < numConnections) {
						for(int j = index+1; j < numConnections; j++) {
							if(plyrConns[j] != null) {
								plyrConns[index] = plyrConns[j];
								pConOuts[index] = pConOuts[j];
								pConReads[index] = pConReads[j];
								plyrConns[j] = null;
								pConOuts[j] = null;
								pConReads[j] = null;
								index++;
							}
						}
					}
					readyForPlay = false;
					return true;
				} catch(Exception why) {
					System.out.println("ConnectionsManager.removeConnection: " + why.getMessage());
					return false;
				}
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("ConnectionsManager.removeConnection: index " + index + " is invalid.");
		}
	}
	/**
	 * Removes a connection at a given port number, if found.
	 * @param port The port number to find.
	 * @return True if the connections was successfully removed.
	 */
	public boolean removeConnectionAtPort(int port) {
		for(int i = 0; i < numConnections; i++) {
			if(plyrConns[i].getPort() == port) {
				try {
					sendMessage(CLOSE_MESSAGE,i);
					plyrConns[i].close();
					plyrConns[i] = null;
					numConnections--;
					pConOuts[i] = null;
					pConReads[i] = null;
					//shift connections left if needed
					if(i < numConnections) {
						for(int j = i+1; j < numConnections; j++) {
							if(plyrConns[j] != null) {
								plyrConns[i] = plyrConns[j];
								pConOuts[i] = pConOuts[j];
								pConReads[i] = pConReads[j];
								
								plyrConns[j] = null;
								pConOuts[j] = null;
								pConReads[j] = null;
								
								i++;
							}
						}
					}
					readyForPlay = false;
					return true;
				} catch(Exception why) {
					System.out.println("ConnectionsManager.removeConnection: " + why.getMessage());
					return false;
				}
			}
		}
		return false;
	}
	/**
	 * Writes a string to the socket at the given index.
	 * @param message The string to write to the socket.
	 * @param index The index of the connection to write to.
	 * @throws Exception Thrown if the socket is not open for writing or if the index is invalid.
	 */
	public void sendMessage(String message, int index) throws Exception {
		if(index < numConnections && index >= 0) {
			if(plyrConns[index] != null) {
				pConOuts[index].println(message);
			} else {
				throw new Exception("ConnectionsManager.sendMessage: Connection at index " + index + " is not writable.");
			}
		} else {
			throw new Exception("ConnectionsManager.sendMessage: index " + index + " is invalid.");
		}
	}
	/**
	 * Sends the message to all open connections.
	 * @param message The message to be sent.
	 */
	public void broadcast(String message) {
		PrintWriter pw;
		for(int i = 1; i<pConOuts.length; i++) {
			pw = pConOuts[i];
			if(pw != null) {
				pw.println(message);
			}
		}
	}
	
	public void closeAll() {
		for(PrintWriter pw : pConOuts) {
			if(pw != null) {
				try {
					pw.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		for(BufferedReader br : pConReads) {
			if(br != null) {
				try {
					br.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		for(Socket s : plyrConns) {
			if(s != null) {
				try {
					s.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	/**
	 * Waits for a response from the connection at index.  Returns the message if one is received within 5 minutes of this call.
	 * @param index The index of the connection to listen to.
	 * @return The received response from the specified connection.
	 * @throws Exception Thrown if either the index is invalid or the client does not respond within 5 minutes.
	 */
//	public String waitForResponse() throws Exception {
//		if(numConnections >= MAX_CONNS) {
//			throw new Exception("ConnectionsManager.waitForResponse: Max connections reached");
//		}
//		//FIX THIS, LISTEN TO ONE PORT
//		pconReads.wait()
//			int numTimeouts = 0;
//			//wait for 30 second increments.  Times out after 300 seconds (5 minutes)
//			while(numTimeouts < 10) {
//				try {
//					pConReads.wait(30000);
//					numTimeouts++;
//				} catch(InterruptedException ie) {
//					break;
//				}
//			}
//			if(numTimeouts == 10) {
//				System.out.println("The client has not responded for 5 minutes.  Timing out.");
//				throw new Exception("ConnectionsManager.waitForResponse: Client timed out, did not respond for 5 minutes.");
//			} else {
//				System.out.println("Client responded!");
//				return pConReads[index].readLine();
//			}
//		} else {
//			throw new Exception("ConnectionsManager.waitForResponse: index " + index + " is invalid.");
//		}
//	}
}
