package turnManagement;

public class Player {
	static boolean turn = true;
	boolean color;
	boolean check;
	
	public Player(boolean turn, boolean color) {
		this.color = color;
	}
	
	public static boolean getTurn() {
		return turn;
	}
	
	public boolean getColor() {
		return color;
	}
	
	public boolean inCheck() {
		return check;
	}
	
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	public static void changeTurn() {
		if (turn == true) {
			turn = false;
		} else {
			turn = true;
		}
	}
}
