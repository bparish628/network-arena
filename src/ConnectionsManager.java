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
	private final int MAX_CONNS = 4; //max PLAYER conns, 0 is default connection
	private final int DEFAULT_PORT = 45560; //used to distribute clients across the actual connection ports
	private final String CLOSE_MESSAGE = "CLOSE"; //sent to tell clients to exit connection
	private final String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|"; //used for telling the main class or client that an error occurred
	private final String hostName = "localhost"; //Not currently used because InetAddress.getByName(null) is better
	//private arrays
	private Socket[] plyrCons; //the actual socket connections
	private BufferedReader[] pConReads; //for reading from a specified socket
	private PrintWriter[] pConOuts; //for sending information to a specified socket
	//private fields
	private byte numConnections; //The number of current player connections
	private boolean readyForPlay; //Should be true if numConnections > 4
	
	//constructors
	/**
	 * Default constructor, used if connections are expected to be added to ports determined after object creation.
	 */
	public ConnectionsManager() {
		//init all player connections, plus one for default ([0])
		plyrCons = new Socket[MAX_CONNS+1];
		pConReads = new BufferedReader[MAX_CONNS+1];
		pConOuts = new PrintWriter[MAX_CONNS+1];
		numConnections = 0;
		readyForPlay = false;
	}
	/**
	 * Full constructor, used if the ports are known when the object is created.
	 * @param portNums Array containing the port numbers that the server is expecting clients to be connected to.
	 */
	public ConnectionsManager(int[] portNums) {
		//if less than 4 port numbers were passed in, then not ready.
		if(portNums.length < 4) {
			readyForPlay = false;
		}
		numConnections = 0;
		//try to connect the sockets.
		plyrCons = new Socket[MAX_CONNS+1];
		pConReads = new BufferedReader[MAX_CONNS+1];
		pConOuts = new PrintWriter[MAX_CONNS+1];
		ServerSocket ss;
		for(int i = 0; i < (portNums.length < 4 ? portNums.length : 4); i++) {
			try {
				System.out.printf("Waiting for connection #%d on port %d\n",i+1,DEFAULT_PORT);
				ss = new ServerSocket(DEFAULT_PORT);
				plyrCons[0] = ss.accept();
				pConOuts[0] = new PrintWriter(plyrCons[0].getOutputStream(), true);
				pConReads[0] = new BufferedReader(new InputStreamReader(plyrCons[0].getInputStream()));
				ss.close();
				String tmpResp = pConReads[0].readLine();
				pConOuts[0].println(portNums[i]);
				closeDefaults();
				if(tmpResp.equals("ACK")) {
					ss = new ServerSocket(portNums[i]);
					plyrCons[i+1] = ss.accept();
					pConReads[i+1] = new BufferedReader(new InputStreamReader(plyrCons[i+1].getInputStream()));
					pConOuts[i+1] = new PrintWriter(plyrCons[i+1].getOutputStream(),true);
					tmpResp = pConReads[i+1].readLine();
					numConnections++;
					System.out.printf("Socket #%d successfully connected!\nMessage: %s\n",i+1,tmpResp);
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
				ServerSocket ss = new ServerSocket(DEFAULT_PORT);
				System.out.printf("Waiting for connection #%d on port %d\n",numConnections+1,DEFAULT_PORT);
				plyrCons[0] = ss.accept();
				pConReads[0] = new BufferedReader(new InputStreamReader(plyrCons[0].getInputStream()));
				pConOuts[0] = new PrintWriter(plyrCons[0].getOutputStream(),true);
				String rsp = pConReads[0].readLine();
				pConOuts[0].println(port);
				closeDefaults();
				plyrCons[numConnections+1] = ss.accept();
				ss.close();
				pConReads[numConnections+1] = new BufferedReader(new InputStreamReader(plyrCons[numConnections+1].getInputStream()));
				pConOuts[numConnections+1] = new PrintWriter(plyrCons[numConnections+1].getOutputStream(),true);
				System.out.printf("Socket #%d successfully connected!\nMessage: %s\n",++numConnections,rsp);
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
			throw new Exception(String.format("%s %s  %s %d\n",
				"ConnectionsManager.addConnection:",
				"Attempted to add more connections than allowed.",
				"Max connections:",
				MAX_CONNS
			));
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
		if(index < numConnections && index > 0) { //0 not allowed because that's the default port
			if(plyrCons[index] == null) {
				System.out.println("DEBUG: Something is wrong.  plyrConns[index] is null but index < numConnections.");
				return false;
			} else {
				try {
					sendMessage(CLOSE_MESSAGE,index);
					pConOuts[index].close();
					pConOuts[index] = null;
					pConReads[index].close();
					pConReads[index] = null;
					plyrCons[index].close();
					plyrCons[index] = null;
					numConnections--;
					
					//The following is commented out because it might actually be a bad idea.
					//Player numbers are most likely going to be associated with an index of plyrCons, so moving them around
					//might be unnecessary and annoying to deal with.

/*					shift connections left if needed
					if(index < numConnections) {
						for(int j = index+1; j < numConnections; j++) {
							if(plyrCons[j] != null) {
								plyrCons[index] = plyrCons[j];
								pConOuts[index] = pConOuts[j];
								pConReads[index] = pConReads[j];
								plyrCons[j] = null;
								pConOuts[j] = null;
								pConReads[j] = null;
								index++;
							}
						}
					}
*/
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
		for(int i = 1; i <= numConnections; i++) {
			if(plyrCons[i].getPort() == port) {
				try {
					sendMessage(CLOSE_MESSAGE,i);
					pConOuts[i].close();
					pConOuts[i] = null;
					pConReads[i].close();
					pConReads[i] = null;
					plyrCons[i].close();
					plyrCons[i] = null;
					numConnections--;
					
					//The following is commented out because it might actually be a bad idea.
					//Player numbers are most likely going to be associated with an index of plyrCons, so moving them around
					//might be unnecessary and annoying to deal with.

/*					shift connections left if needed
					if(index < numConnections) {
						for(int j = index+1; j < numConnections; j++) {
							if(plyrCons[j] != null) {
								plyrCons[index] = plyrCons[j];
								pConOuts[index] = pConOuts[j];
								pConReads[index] = pConReads[j];
								plyrCons[j] = null;
								pConOuts[j] = null;
								pConReads[j] = null;
								index++;
							}
						}
					}
*/
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
			if(plyrCons[index] != null) {
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
		for(int i = 1; i<pConOuts.length; i++) {
			if(pConOuts[i] != null) {
				pConOuts[i].println(message);
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
		for(Socket s : plyrCons) {
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
	 * Waits for a response from the connection at index.  Returns the message received.
	 * @param index The index of the connection to listen to.
	 * @return The received response from the specified connection.
	 */
	public String waitForResponse(int index) {
		String response;
		try {		
			response = pConReads[index].readLine();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			response = ERROR_MESSAGE;
		}
		return response;
	}
	
	//private methods
	/**
	 * Closes the default Socket, BufferedReader, and PrintWriter if they are not null.
	 */
	private void closeDefaults() {
		try {
			if(!(pConOuts[0] == null)) {
				pConOuts[0].close();
			}
			if(!(pConReads[0] == null)) {
				pConReads[0].close();
			}
			if(!(plyrCons[0] == null) && !plyrCons[0].isClosed()) {
				plyrCons[0].close();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}