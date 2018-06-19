package boardGUIManager;

import java.util.ArrayList;
import java.util.Iterator;

import pieceRules.Bishop;
import pieceRules.Horse;
import pieceRules.King;
import pieceRules.Pawn;
import pieceRules.Piece;
import pieceRules.Queen;
import pieceRules.Rook;
import turnManagement.Player;

public class PieceObjectManager {

	static Player white = new Player(true, true);
	static Player black = new Player(false, false);
	Piece attackingPiece;
	Piece defendingPiece;
	Piece[][] savedMatrix;
	static Piece[][] pieceMatrix = {
			{ new Rook(black, 0, 0), new Horse(black, 1, 0), new Bishop(black, 2, 0), new Queen(black, 3, 0),
					new King(black, 4, 0), new Bishop(black, 5, 0), new Horse(black, 6, 0), new Rook(black, 7, 0) },
			{ new Pawn(black, 0, 1), new Pawn(black, 1, 1), new Pawn(black, 2, 1), new Pawn(black, 3, 1),
					new Pawn(black, 4, 1), new Pawn(black, 5, 1), new Pawn(black, 6, 1), new Pawn(black, 7, 1) },
			{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
			{ new Pawn(white, 0, 6), new Pawn(white, 1, 6), new Pawn(white, 2, 6), new Pawn(white, 3, 6),
					new Pawn(white, 4, 6), new Pawn(white, 5, 6), new Pawn(white, 6, 6), new Pawn(white, 7, 6) },
			{ new Rook(white, 0, 7), new Horse(white, 1, 7), new Bishop(white, 2, 7), new Queen(white, 3, 7),
					new King(white, 4, 7), new Bishop(white, 5, 7), new Horse(white, 6, 7), new Rook(white, 7, 7) } };

	/**
	 * Get a row of the chessboard.
	 * 
	 * @param y
	 *            determines which row, from 0 (top row) to 7 (bottom row).
	 * @return an array of Piece containing the Pieces on the specified row. If a
	 *         tile is empty, null is added to the row array.
	 */
	public static Piece[] getRow(int y) {
		return pieceMatrix[y];
	}

	/**
	 * Get a column of the chessboard.
	 * 
	 * @param x
	 *            determines which column, from 0 (left column) to 7 (right column).
	 * @return an array of Piece containing the Pieces on the specified column. If a
	 *         tile is empty, null is added to the row array.
	 */
	public static Piece[] getColumn(int x) {
		Piece[] column = new Piece[8];
		for (int i = 0; i < pieceMatrix.length; i++) {
			column[i] = pieceMatrix[i][x];
		}
		return column;
	}

	/**
	 * Get the piece at the specified x/y location.
	 * 
	 * @param x
	 *            X value of the x/y location.
	 * @param y
	 *            Y value of the x/y location.
	 * @return the Piece located at the specified x/y location. If there is no
	 *         piece, returns null.
	 */
	public static Piece getPiece(int x, int y) {
		return pieceMatrix[y][x];
	}

	public static Piece getPiece(int location) {
		try {
			int y = (location / 8);
			int x = (location % 8);
			return pieceMatrix[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param previousX
	 *            X value of the piece's starting position
	 * @param previousY
	 *            Y value of the piece's starting position
	 * @param x
	 *            X value of the piece's destination
	 * @param y
	 *            Y value of the piece's destination
	 * @param temporary
	 *            determines whether or not the move should be considered permanent
	 *            (for check checking)
	 */
	public void movePiece(int previousX, int previousY, int x, int y, boolean temporary) {
		attackingPiece = getPiece(previousX, previousY);
		defendingPiece = getPiece(x, y);
		pieceMatrix[y][x] = attackingPiece;
		pieceMatrix[previousY][previousX] = null;
		if (temporary) {
			attackingPiece.tempMove(x, y);
		} else {
			attackingPiece.move(x, y);
		}
	}

	/**
	 * A helper method that lets the programmer call movePiece without having to
	 * worry about translating 0-63 into x/y.
	 * 
	 * @param previousLocation
	 *            0-63 location of the piece's starting position
	 * @param location
	 *            0-63 of the piece's destination
	 * @param temporary
	 *            determines whether or not the move should be considered permanent
	 *            (for check checking)
	 */
	public void movePiece(int previousLocation, int location, boolean temporary) {
		int y = (location / 8);
		int x = (location % 8);
		int previousY = (previousLocation / 8);
		int previousX = (previousLocation % 8);
		movePiece(previousX, previousY, x, y, temporary);
	}

	/**
	 * Undoes a move. Used when finding moves that cancel check.
	 * 
	 * @param previousX
	 *            X value of the location the piece returns to
	 * @param previousY
	 *            Y value of the location the piece returns to
	 * @param x
	 *            X value of the undone destination
	 * @param y
	 *            Y value of the undone destination
	 */
	public void undoMove(int previousX, int previousY, int x, int y) {
		pieceMatrix[y][x] = defendingPiece;
		pieceMatrix[previousY][previousX] = attackingPiece;
		loadPieceMatrix();
	}

	/**
	 * A helper method that allows the programmer to call undoMove without worrying
	 * about translation from 0-63 to x/y.
	 * 
	 * @param previousLocation
	 *            0-63 location that the piece returns to
	 * @param location
	 *            the undone 0-63 destination
	 */
	public void undoMove(int previousLocation, int location) {
		int y = (location / 8);
		int x = (location % 8);
		int previousY = (previousLocation / 8);
		int previousX = (previousLocation % 8);
		undoMove(previousX, previousY, x, y);
	}

	/**
	 * Returns an arraylist of the 0-63 possible moves of the piece found at int
	 * location. This list DOES NOT INCLUDE moves that take another piece.
	 * 
	 * @param location
	 *            the 0-63 location of the piece whose moves are being checked
	 * @return an ArrayList<Integer> containing the 0-63 locations of the piece's
	 *         moves. If no piece is found at the parameter location, returns an
	 *         empty ArrayList<Integer>.
	 */
	public ArrayList<Integer> getMoves(int location) {
		Piece piece = getPiece(location);
		if (piece != null) {
			ArrayList<Integer> destinations = piece.checkMoves();
			Iterator<Integer> it = destinations.iterator();
			while (it.hasNext()) {
				int x = it.next();
				Piece otherPiece = getPiece(x);
				if (otherPiece != null) {
					it.remove();
				}
			}

			// If piece is a king, we need to check for castling.
			if (piece instanceof King) {
				King king = (King) piece;
				if (king.leftRookCastle()) {
					destinations.add(getLocation(king.getX() - 2, king.getY()));
				}
				if (king.rightRookCastle()) {
					destinations.add(getLocation(king.getX() + 2, king.getY()));
				}
			}

			return destinations;
		} else {
			return new ArrayList<Integer>();
		}
	}

	/**
	 * Returns an ArrayList<Integer> containing the 0-63 possible attacks of the
	 * piece located at parameter location. If there are no attacks or the location
	 * does not contain a piece, returns an empty ArrayList<Integer>.
	 * 
	 * @param location
	 *            the 0-63 location of the piece whose attacks are being checked.
	 * @return an ArrayList<Integer> containing the possible 0-63 moves that would
	 *         result in an enemy piece being taken.
	 */
	public ArrayList<Integer> getAttacks(int location) {
		Piece piece = getPiece(location);
		if (piece != null) {
			ArrayList<Integer> attacks = piece.checkAttacks();

			Piece otherPiece;
			Iterator<Integer> it = attacks.iterator();
			while (it.hasNext()) {
				otherPiece = getPiece(it.next());
				if (otherPiece == null || piece.sameColor(otherPiece)) {
					it.remove();
				}
			}
			return attacks;
		} else {
			return new ArrayList<Integer>();
		}
	}

	/**
	 * A helper method that translates the location from x/y to 0-63.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getLocation(int x, int y) {
		return ((y * 8) + x);
	}

	/**
	 * A helper method that translates the location from 0-63 to x/y.
	 * 
	 * @param location
	 *            the location, from 0 to 63
	 * @return an array of ints, with the first element containing x, and the second
	 *         containing y
	 */
	public static int[] getLocation(int location) {
		int[] arrayToReturn = new int[2];
		arrayToReturn[0] = (location % 8);
		arrayToReturn[1] = (location / 8);
		return arrayToReturn;
	}

	/**
	 * Determines if the x/y location given is a valid location on the board.
	 * 
	 * @param x
	 *            X value of the location
	 * @param y
	 *            Y value of the location
	 * @return true if the location is valid, false if not.
	 */
	public static boolean validLocation(int x, int y) {
		if (x >= 8 || x < 0) {
			return false;
		}
		if (y >= 8 || y < 0) {
			return false;
		}
		return (getLocation(x, y) >= 0 && getLocation(x, y) < 64);
	}

	/**
	 * Determine if there is a king in danger.
	 * 
	 * @return true if a king is in check, false if not.
	 */
	public boolean checkAllPiecesForCheck() {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				Piece piece = pieceMatrix[j][i];
				if (piece != null) {
					ArrayList<Integer> possibleThreats = piece.checkAttacks();

					for (Integer location : possibleThreats) {
						if (getPiece(location) instanceof King) {
							if (!getPiece(location).sameColor(piece)) {
								getPiece(location).getPlayer().setCheck(true);
								return true;
							}
						}
					}
				}
			}
		}
		if (Player.getTurn()) {
			black.setCheck(false);
		} else {
			white.setCheck(false);
		}
		return false;
	}

	/**
	 * Saves the current state of the game.
	 */
	public void savePieceMatrix() {
		savedMatrix = pieceMatrix;
	}

	/**
	 * Loads the current state of the game.
	 */
	public void loadPieceMatrix() {
		pieceMatrix = savedMatrix;
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if (pieceMatrix[j][i] != null) {
					pieceMatrix[j][i].tempMove(i, j);
				}
			}
		}
	}

	/**
	 * Replaces the piece at parameter location with parameter piece.
	 * 
	 * @param location
	 *            the location to be replaced.
	 * @param piece
	 *            the piece to place at param location.
	 */
	public void replacePiece(int location, Piece piece) {
		int[] locationXY = getLocation(location);
		pieceMatrix[locationXY[1]][locationXY[0]] = piece;
	}
}
