package pieceRules;

import java.util.ArrayList;

import boardGUIManager.PieceObjectManager;
import turnManagement.Player;

public class Queen extends Piece {

	private ArrayList<Integer> attacks;

	public Queen(Player color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<Integer> checkMoves() {
		attacks = new ArrayList<Integer>();
		ArrayList<Integer> moves = new ArrayList<Integer>();
		int checkX = getX();
		int checkY = getY();
		while (true) {
			checkX++;
			checkY++;
			if (PieceObjectManager.validLocation(checkX, checkY)) {
				if (PieceObjectManager.getPiece(checkX, checkY) == null) {
					moves.add(PieceObjectManager.getLocation(checkX, checkY));
				} else if (isWhite() == PieceObjectManager.getPiece(checkX, checkY).isWhite()) {
					break;
				} else {
					attacks.add(PieceObjectManager.getLocation(checkX, checkY));
					break;
				}
			} else {
				break;
			}
		}

		checkX = getX();
		checkY = getY();
		while (true) {
			checkX--;
			checkY++;
			if (PieceObjectManager.validLocation(checkX, checkY)) {
				if (PieceObjectManager.getPiece(checkX, checkY) == null) {
					moves.add(PieceObjectManager.getLocation(checkX, checkY));
				} else if (isWhite() == PieceObjectManager.getPiece(checkX, checkY).isWhite()) {
					break;
				} else {
					attacks.add(PieceObjectManager.getLocation(checkX, checkY));
					break;
				}
			} else {
				break;
			}
		}

		checkX = getX();
		checkY = getY();
		while (true) {
			checkX++;
			checkY--;
			if (PieceObjectManager.validLocation(checkX, checkY)) {
				if (PieceObjectManager.getPiece(checkX, checkY) == null) {
					moves.add(PieceObjectManager.getLocation(checkX, checkY));
				} else if (isWhite() == PieceObjectManager.getPiece(checkX, checkY).isWhite()) {
					break;
				} else {
					attacks.add(PieceObjectManager.getLocation(checkX, checkY));
					break;
				}
			} else {
				break;
			}
		}

		checkX = getX();
		checkY = getY();
		while (true) {
			checkX--;
			checkY--;
			if (PieceObjectManager.validLocation(checkX, checkY)) {
				if (PieceObjectManager.getPiece(checkX, checkY) == null) {
					moves.add(PieceObjectManager.getLocation(checkX, checkY));
				} else if (isWhite() == PieceObjectManager.getPiece(checkX, checkY).isWhite()) {
					break;
				} else {
					attacks.add(PieceObjectManager.getLocation(checkX, checkY));
					break;
				}
			} else {
				break;
			}
		}

		Piece[] row = PieceObjectManager.getRow(getY());
		checkX = getX() + 1;
		while (checkX < 8) {
			if (row[checkX] == null) {
				moves.add(PieceObjectManager.getLocation(checkX, getY()));
			} else if (row[checkX].sameColor(this)) {
				break;
			} else if (!row[checkX].sameColor(this)) {
				attacks.add(PieceObjectManager.getLocation(checkX, getY()));
				break;
			}
			checkX++;
		}
		checkX = getX() - 1;
		while (checkX >= 0) {
			if (row[checkX] == null) {
				moves.add(PieceObjectManager.getLocation(checkX, getY()));
			} else if (row[checkX].sameColor(this)) {
				break;
			} else if (!row[checkX].sameColor(this)) {
				attacks.add(PieceObjectManager.getLocation(checkX, getY()));
				break;
			}
			checkX--;
		}

		Piece[] column = PieceObjectManager.getColumn(getX());
		checkY = getY() + 1;
		while (checkY < 8) {
			if (column[checkY] == null) {
				moves.add(PieceObjectManager.getLocation(getX(), checkY));
			} else if (column[checkY].sameColor(this)) {
				break;
			} else if (!column[checkY].sameColor(this)) {
				attacks.add(PieceObjectManager.getLocation(getX(), checkY));
				break;
			}
			checkY++;
		}
		checkY = getY() - 1;
		while (checkY >= 0) {
			if (column[checkY] == null) {
				moves.add(PieceObjectManager.getLocation(getX(), checkY));
			} else if (isWhite() == column[checkY].isWhite()) {
				break;
			} else {
				attacks.add(PieceObjectManager.getLocation(getX(), checkY));
				break;
			}
			checkY--;
		}
		return moves;
	}

	@Override
	public ArrayList<Integer> checkAttacks() {
		checkMoves();
		return attacks;
	}
}
