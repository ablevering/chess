package pieceRules;

import java.util.ArrayList;

import boardGUIManager.PieceObjectManager;
import turnManagement.Player;

public class King extends Piece {

	private boolean moved = false;

	public King(Player color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<Integer> checkMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		if (getX() + 1 < 8) {
			moves.add(PieceObjectManager.getLocation(getX() + 1, getY()));
			if (getY() + 1 < 8)
				moves.add(PieceObjectManager.getLocation(getX() + 1, getY() + 1));
			if (getY() - 1 >= 0)
				moves.add(PieceObjectManager.getLocation(getX() + 1, getY() - 1));
		}
		if (getX() - 1 >= 0) {
			moves.add(PieceObjectManager.getLocation(getX() - 1, getY()));
			if (getY() + 1 < 8)
				moves.add(PieceObjectManager.getLocation(getX() - 1, getY() + 1));
			if (getY() - 1 >= 0)
				moves.add(PieceObjectManager.getLocation(getX() - 1, getY() - 1));
		}
		if (getY() + 1 < 8)
			moves.add(PieceObjectManager.getLocation(getX(), getY() + 1));
		if (getY() - 1 >= 0)
			moves.add(PieceObjectManager.getLocation(getX(), getY() - 1));

		return moves;
	}

	/**
	 * Determines whether or not the king is able to perform a left castle.
	 * @return true if the king can perform a castle with the left rook. 
	 */
	public boolean leftRookCastle() {
		// We need to check if the king can perform a castle.
		if (!moved()) {
			// Check the rook on the left side of the board to see if it has moved.
			int x = 0;
			int y = getY();
			if (PieceObjectManager.getPiece(x, y) != null && PieceObjectManager.getPiece(x, y) instanceof Rook
					&& !getPlayer().inCheck()) {
				Rook leftRook = (Rook) PieceObjectManager.getPiece(x, y);
				if (!leftRook.moved()) {
					// Now, we know that neither the king nor the rook has moved. We need to check
					// if the
					// pieces between them are null; if not, castling is not an option.
					while (x < getX() - 1) {
						x++;
						if (PieceObjectManager.getPiece(x, y) != null || !checkCastlingPath(PieceObjectManager.getLocation(x, y))) {
							return false;
						}
					}
					// leftRook castling is an option, so long as it does not put the king in check.
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines if the king can castle with the rook to his right.
	 * @return true if the right rook castle is available.
	 */
	public boolean rightRookCastle() {
		// We need to check if the king can perform a castle.
		if (!moved()) {
			// Check the rook on the left side of the board to see if it has moved.
			int x = 7;
			int y = getY();
			if (PieceObjectManager.getPiece(x, y) != null && PieceObjectManager.getPiece(x, y) instanceof Rook
					&& !getPlayer().inCheck()) {
				Rook leftRook = (Rook) PieceObjectManager.getPiece(x, y);
				if (!leftRook.moved()) {
					// Now, we know that neither the king nor the rook has moved. We need to check
					// if the
					// pieces between them are null; if not, castling is not an option.
					while (x > getX() + 1) {
						x--;
						if (PieceObjectManager.getPiece(x, y) != null || !checkCastlingPath(PieceObjectManager.getLocation(x, y))) {
							return false;
						}
					}
					// rightRook castling is an option, so long as it does not put the king in
					// check.
					return true;
				}
			}
		}
		return false;
	}

	public void move() {
		moved = true;
	}

	public boolean moved() {
		return moved;
	}

	@Override
	public void tempMove(int x, int y) {
		super.move(x, y);
	}

	@Override
	public void move(int x, int y) {
		move();
		super.move(x, y);
	}

	private boolean checkCastlingPath(int location) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				Piece piece = PieceObjectManager.getPiece(j, i);
				if (piece != null) {
					ArrayList<Integer> possibleThreats = piece.checkAttacks();

					for (Integer attack : possibleThreats) {
						if (location == attack && !this.sameColor(piece)) {
							return false;
						}
					}

				}
			}
		}
		return true;
	}
}
