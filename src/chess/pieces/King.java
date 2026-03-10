package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch match;

	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		// Above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Above+Right
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Above+Left
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Below+Left
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Below+Right
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p) && !match.squareUnderAttack(p, getColor())) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Castling Move
		if (getMoveCount() == 0 && !match.getCheck()) {
			// Kingside
			Position positionRook1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(positionRook1)) {
				Position firstFreeSpace = new Position(position.getRow(), position.getColumn() + 1);
				Position secondFreeSpace = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(firstFreeSpace) == null && getBoard().piece(secondFreeSpace) == null
						&& !match.squareUnderAttack(firstFreeSpace, getColor())
						&& !match.squareUnderAttack(secondFreeSpace, getColor())) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// Queenside
			Position positionRook2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(positionRook2)) {
				Position firstFreeSpace = new Position(position.getRow(), position.getColumn() - 1);
				Position secondFreeSpace = new Position(position.getRow(), position.getColumn() - 2);
				Position thirdFreeSpace = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(firstFreeSpace) == null && getBoard().piece(secondFreeSpace) == null
						&& getBoard().piece(thirdFreeSpace) == null
						&& !match.squareUnderAttack(firstFreeSpace, getColor())
						&& !match.squareUnderAttack(secondFreeSpace, getColor())) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}

		return mat;
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

}
