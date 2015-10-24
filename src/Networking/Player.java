package Networking;

public class Player {
	private final static int MAX_PLAYERS = 4;
	private String name;
	private int num;
	
	public Player(String pName, int pNum) {
		this.name = pName;
		this.num = pNum;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String pName) throws Exception {
		if(pName != null && pName.length() != 0) {
			if(pName.length() > 20) {
				this.name = pName.substring(0, 19);
			} else {
				this.name = pName;
			}
		} else {
			throw new Exception("Player.setName: Name was not initialized or empty.");
		}
	}
	public int getNum() {
		return this.num;
	}
	public void setNum(int pNum) throws Exception {
		if(pNum <= 0 || pNum > MAX_PLAYERS) {
			throw new Exception("Player.setNum: Number is invalid.");
		} else {
			this.num = pNum;
		}
	}
	@Override
	public String toString() {
		return String.format("Player #%d: %s\n", this.num, this.name);
	}
}
