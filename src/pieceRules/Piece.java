package pieceRules;

import java.util.ArrayList;
import turnManagement.Player;

public abstract class Piece {
	private Player player;
	private int x;
	private int y;

	public Piece(Player player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
	}

	/**
	 * Finds the integer locations of all possible empty destinations for a piece
	 * and stores them into an ArrayList.
	 * 
	 * @return an ArrayList containing the integer locations of all possible moves
	 *         for a piece.
	 */
	public abstract ArrayList<Integer> checkMoves();

	/**
	 * Finds the integer locations of all attacking destinations for a piece and
	 * stores them into an ArrayList. The ArrayList is empty if the piece doesn't
	 * override checkAttacks() with specifics.
	 * 
	 * @return an ArrayList containing all the possible moves that are specific to
	 *         attacks.
	 */
	public ArrayList<Integer> checkAttacks() {
		return checkMoves();
	}

	/**
	 * Determines if two pieces have the same color.
	 * 
	 * @param otherPiece
	 *            the piece being compared with this piece.
	 * @return true if the pieces are the same color, false if not.
	 */
	public boolean sameColor(Piece otherPiece) {
		return (isWhite() == otherPiece.isWhite());
	}

	/**
	 * Some pieces have to keep track of whether or not they have moved. Those
	 * pieces override tempMove. tempMove is to be used when checking for check.
	 * 
	 * @param x
	 *            x value of the x/y destination
	 * @param y
	 *            y value of the x/y destination
	 */
	public void tempMove(int x, int y) {
		move(x, y);
	}

	/**
	 * This method is the permanent move; pieces that have to keep track of whether
	 * or not they have moved override this method. They change their boolean when
	 * this is called.
	 * 
	 * @param x
	 *            x value of the x/y destination
	 * @param y
	 *            y value fo the x/y destination
	 */
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isWhite() {
		return player.getColor();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Player getPlayer() {
		return player;
	}
}
