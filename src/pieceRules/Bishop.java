package pieceRules;

import java.util.ArrayList;

import boardGUIManager.PieceObjectManager;
import turnManagement.Player;

public class Bishop extends Piece {
	
	private ArrayList<Integer> attacks;

	public Bishop(Player color, int x, int y) {
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
		return moves;
	}

	@Override
	public ArrayList<Integer> checkAttacks() {
		checkMoves();
		return attacks;
		
	}
}
