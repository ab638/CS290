package chess;

import boardgame.Cell;
import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;


public abstract class ChessPiece extends Cell implements Cloneable {
    public boolean moved;
    Colors pieceName;

    public ChessPiece(Position position, Colors pieceName, Colors color) {
        super( position, color );
        this.pieceName = pieceName;
        this.moved = false;
    }


    public ChessPiece() {
        super(); // to get rid of super error
    }

    public Colors getPieceName() {
        return this.pieceName;
    }

    public void setMoved() {
        this.moved = true;
    }

    public boolean isKing() {
        return false;
    }

    public boolean hasMoved() {
        return moved;
    }

    @Override
    public String toString() {
        switch (pieceName) {
            case PAWN:
                return (colors == Colors.WHITE) ? "p" : "P";
            case QUEEN:
                return (colors == Colors.WHITE) ? "q" : "Q";
            case KING:
                return (colors == Colors.WHITE) ? "k" : "K";
            case ROOK:
                return (colors == Colors.WHITE) ? "r" : "R";
            case BISHOP:
                return (colors == Colors.WHITE) ? "b" : "B";
            case KNIGHT:
                return (colors == Colors.WHITE) ? "n" : "N";
            case UNDER:
                return "_";
            case POUND:
                return "#";
        }
        return null;
    }

    public boolean checkPawn() {
        return false;
    }

    public Position getPosition() {
        return position;
    }

    public abstract HashSet<ChessMove> addMoves(ChessBoard board);

    public ChessPiece clone(Colors player) {
        return null;
    }


    public abstract boolean isNull();

    public void setNewLocation(Position to) {
        this.position = to;
    }
}
