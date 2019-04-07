package chess;

import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;

public class EmptyChessPiece extends ChessPiece {
    public EmptyChessPiece(Position position, Colors color) {
        this.position = position;
        this.colors = this.pieceName = color;
    }

    @Override
    public HashSet<ChessMove> addMoves(ChessBoard board) {
        return null;
    }

    @Override
    public ChessPiece clone(Colors player) {
        return null;
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
