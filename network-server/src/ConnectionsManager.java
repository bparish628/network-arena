import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Timer;
import common.*;
import events.EventsController;
import sockets.ServerListener;

/**
 * This class is designed to make interacting with the four sockets easier.
 * @author Kale
 */
public class ConnectionsManager {
	
	//constants
	private final int MAX_CONNS = 4; //max PLAYER conns, 0 is default connection
	private final int DEFAULT_PORT = 55560; //used to distribute clients across the actual connection ports
	private final String CLOSE_MESSAGE = "CLOSE"; //sent to tell clients to exit connection
	private final String ERROR_MESSAGE = "|ERROR|ERROR|ERROR|"; //used for telling the main class or client that an error occurred
	private final String hostName = "localhost"; //Not currently used because InetAddress.getByName(null) is better
	private final static String PW = "ARENA";
	//private arrays
	private Socket[] plyrCons; //the actual socket connections
	private ObjectInputStream[] pConReads; //for reading from a specified socket
	private ObjectOutputStream[] pConOuts; //for sending information to a specified socket
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
		pConReads = new ObjectInputStream[MAX_CONNS+1];
		pConOuts = new ObjectOutputStream[MAX_CONNS+1];
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
		pConOuts = new ObjectOutputStream[MAX_CONNS+1];
		ServerSocket ss;
		pConReads = new ObjectInputStream[MAX_CONNS+1];
		for(int i = 0; i < (portNums.length < 4 ? portNums.length : 4); i++) {
			try {
				System.out.printf("Waiting for connection #%d on port %d\n",i+1,DEFAULT_PORT);
				ss = new ServerSocket(DEFAULT_PORT);
				plyrCons[0] = ss.accept();
				pConOuts[0] = new ObjectOutputStream(plyrCons[0].getOutputStream());
				pConReads[0] = new ObjectInputStream(plyrCons[0].getInputStream());
				ss.close();
				String tmpResp = (String)pConReads[0].readObject();
				pConOuts[0].writeInt(portNums[i]);
				closeDefaults();
				if(tmpResp.equals("ACK")) {
					ss = new ServerSocket(portNums[i]);
					plyrCons[i+1] = ss.accept();
					pConReads[i+1] = new ObjectInputStream(plyrCons[i+1].getInputStream());
					pConOuts[i+1] = new ObjectOutputStream(plyrCons[i+1].getOutputStream());
					tmpResp = (String)pConReads[i+1].readObject();
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
	 * @return The response from the client when connection to port is established.
	 */
	public Player addConnection(int port) throws Exception {
		if(numConnections < MAX_CONNS) {
			try {
				ServerSocket ss = new ServerSocket(DEFAULT_PORT);
				System.out.printf("Waiting for connection #%d on port %d\n",numConnections+1,DEFAULT_PORT);
				plyrCons[0] = ss.accept();
				pConReads[0] = new ObjectInputStream(plyrCons[0].getInputStream());
				pConOuts[0] = new ObjectOutputStream(plyrCons[0].getOutputStream());
				String rsp = (String)pConReads[0].readObject(); //pw
				if(rsp.equals(PW)) {
					pConOuts[0].writeInt(port);
				} else {
					pConOuts[0].writeInt(-1); //incorrect pw
					closeDefaults();
					ss.close();
					throw new Exception("Invalid password");
				}
				closeDefaults();
				ss.close();
				ss = new ServerSocket(port);
				plyrCons[numConnections+1] = ss.accept();
				ss.close();
				pConReads[numConnections+1] = new ObjectInputStream(plyrCons[numConnections+1].getInputStream());
				pConOuts[numConnections+1] = new ObjectOutputStream(plyrCons[numConnections+1].getOutputStream());
				Player prsp = (Player)pConReads[numConnections+1].readObject();
				prsp.setPlayerNum(numConnections+1);
				pConOuts[numConnections+1].writeObject(numConnections + 1);
				System.out.printf("Socket #%d successfully connected!\nMessage: %s\n",++numConnections,prsp);
				if(numConnections == 4) {
					readyForPlay = true;
				}
				return prsp;
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
	public void sendMessage(Object message, int index) throws Exception {
		if(index < numConnections+1 && index >= 0) {
			if(plyrCons[index] != null) {
				pConOuts[index].writeObject(message);
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
	public void broadcast(Object message) {
		for(int i = 1; i<pConOuts.length; i++) {
			if(pConOuts[i] != null) {
				try {
					pConOuts[i].writeObject(message);
					pConOuts[i].reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
		}
	}
	
	public void closeAll() {
		for(ObjectOutputStream pw : pConOuts) {
			if(pw != null) {
				try {
					pw.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		for(ObjectInputStream br : pConReads) {
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
	public Object waitForResponse(int index) {
		Object response;
		try {		
			response = pConReads[index].readObject();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			response = ERROR_MESSAGE;
		}
		return response;
	}
	
	//private methods
	/**
	 * Closes the default Socket, ObjectInputStream, and ObjectOutputStream if they are not null.
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

	public void startBattle(Player[] players){
		boolean ended = false;
		ServerRequest request;

		while(!ended){
			for(int i = 1; i <= players.length; i++){

				try{
					if(!players[i-1].isAlive()) {
						pConOuts[i].close();
						pConReads[i].close();
						plyrCons[i].close();
						continue;
					}
					pConOuts[i].reset();
					pConOuts[i].writeObject("Your turn");
					request = (ServerRequest) pConReads[i].readObject();
					Player[] playersArray = players;
					Player attacker = new Player();
					Player target = new Player();

					int attackerJ = 0;
					int targetJ = 0;

					//Find the 2 players
					for (int j = 0; j < players.length; j++) {
						if (players[j].getPlayerNum() == request.getAttackerNum()) {
							attacker = players[j];
							attackerJ = j;
						}
						if (players[j].getPlayerNum() == request.getTargetNum()) {
							target = players[j];
							targetJ = j;
						}
					}

					//Perform Action
					switch (request.getSelectedAction().getType()) {
						case "Basic":
							Combat.attack(attacker, target);
							break;
						case "Guard":
							Combat.guard(attacker);
							break;
						case "Special":
							Combat.special(attacker, players);
							break;
					}
					System.out.println(attacker.getUsername() + " is attacking " + target.getUsername());


					playersArray[attackerJ] = attacker;
					playersArray[targetJ] = target;

					System.out.println("Sending users.");

					broadcast(playersArray);

					try {
						Thread.sleep(2000);
					} catch (InterruptedException ie) {
						System.out.println("Interrupted.");
					}
				}catch(Exception e){

				}

			}

		}
	}
}