package pieceRules;

import java.util.ArrayList;

import boardGUIManager.PieceObjectManager;
import turnManagement.Player;

public class Pawn extends Piece {

	private boolean moved;

	public Pawn(Player black, int x, int y) {
		super(black, x, y);
	}

	@Override
	public ArrayList<Integer> checkMoves() {
		ArrayList<Integer> moveList = new ArrayList<Integer>();
		if (isWhite()) {
			if ((PieceObjectManager.validLocation(getX(), getY() - 1))) {
				moveList.add(PieceObjectManager.getLocation(getX(), getY() - 1));
				if (!moved && PieceObjectManager.getPiece(getX(), getY() - 1) == null
						&& PieceObjectManager.validLocation(getX(), getY() - 2)
						&& PieceObjectManager.getPiece(getX(), getY() - 2) == null) {
					moveList.add(PieceObjectManager.getLocation(getX(), (getY() - 2)));
				}
			}
		} else {
			if (PieceObjectManager.validLocation(getX(), getY() + 1)) {
				moveList.add(PieceObjectManager.getLocation(getX(), (getY() + 1)));
				if (!moved && PieceObjectManager.getPiece(getX(), getY() + 1) == null
						&& PieceObjectManager.validLocation(getX(), getY() + 2)
						&& PieceObjectManager.getPiece(getX(), getY() + 2) == null) {
					moveList.add(PieceObjectManager.getLocation(getX(), (getY() + 2)));
				}
			}
		}
		return moveList;
	}

	@Override
	public ArrayList<Integer> checkAttacks() {
		ArrayList<Integer> attackList = new ArrayList<Integer>();
		if (isWhite()) {
			if ((getY() - 1) >= 0) {
				if (getX() + 1 < 8)
					attackList.add(PieceObjectManager.getLocation(getX() + 1, getY() - 1));
				if (getX() - 1 >= 0)
					attackList.add(PieceObjectManager.getLocation(getX() - 1, getY() - 1));
			}
		} else {
			if ((getY() + 1) < 8) {
				if (getX() + 1 < 8)
					attackList.add(PieceObjectManager.getLocation(getX() + 1, (getY() + 1)));
				if (getX() - 1 >= 0)
					attackList.add(PieceObjectManager.getLocation(getX() - 1, (getY() + 1)));
			}
		}
		return attackList;
	}

	@Override
	public void move(int x, int y) {
		super.move(x, y);
		moved = true;
	}
	
	@Override
	public void tempMove(int x, int y) {
		super.move(x, y);
	}

	public void unMove() {
		moved = false;
	}
}
