package pieceRules;

import java.util.ArrayList;

import boardGUIManager.PieceObjectManager;
import turnManagement.Player;

public class Horse extends Piece {

	public Horse(Player color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<Integer> checkMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		if (getX() + 2 < 8 && getY() + 1 < 8)
			moves.add(PieceObjectManager.getLocation(getX() + 2, getY() + 1));
		if (getX() + 2 < 8 && getY() - 1 >= 0)
			moves.add(PieceObjectManager.getLocation(getX() + 2, getY() - 1));
		if (getX() - 2 >= 0 && getY() + 1 < 8)
			moves.add(PieceObjectManager.getLocation(getX() - 2, getY() + 1));
		if (getX() - 2 >= 0 && getY() - 1 >= 0)
			moves.add(PieceObjectManager.getLocation(getX() - 2, getY() - 1));
		if (getY() + 2 < 8 && getX() + 1 < 8)
			moves.add(PieceObjectManager.getLocation(getX() + 1, getY() + 2));
		if (getY() + 2 < 8 && getX() -1 >= 0)
			moves.add(PieceObjectManager.getLocation(getX() - 1, getY() + 2));
		if (getY() - 2 >= 0 && getX() + 1 < 8)
			moves.add(PieceObjectManager.getLocation(getX() + 1, getY() - 2));
		if (getY() - 2 >= 0 && getX() - 1 >= 0)
			moves.add(PieceObjectManager.getLocation(getX() - 1, getY() - 2));

		return moves;
	}
}
