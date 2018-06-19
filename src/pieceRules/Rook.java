package pieceRules;

import java.util.ArrayList;

import boardGUIManager.PieceObjectManager;
import turnManagement.Player;

public class Rook extends Piece {

	private boolean moved;
	private ArrayList<Integer> attacks;
	
	public Rook(Player color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<Integer> checkMoves() {
		attacks = new ArrayList<Integer>();
		ArrayList<Integer> moves = new ArrayList<Integer>();
		Piece[] row = PieceObjectManager.getRow(getY());
		int checkX = getX() + 1;
		while (checkX < 8) {
			if(row[checkX] == null) {
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
			if(row[checkX] == null) {
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
		int checkY = getY() + 1;
		while (checkY < 8) {
			if(column[checkY] == null) {
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
			if(column[checkY] == null) {
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
	
	public boolean moved() {
		return moved;
	}
	
	public void move() {
		moved = true;
	}
	
	@Override
	public ArrayList<Integer> checkAttacks() {
		checkMoves();
		return attacks;
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
}
