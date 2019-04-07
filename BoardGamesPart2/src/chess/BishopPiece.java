package chess;

import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;

public class BishopPiece extends ChessPiece {
    public BishopPiece(Position position, Colors type) {
        super( position, Colors.BISHOP, type );
    }

    @Override
    public HashSet<ChessMove> addMoves(ChessBoard board) {
        HashSet<ChessMove> move = new HashSet<>();
        Position from;
        Position to;

        from = new Position( position.getX(), position.getY() );
        Colors player = getColors();

        int i = 1;
        while (from.getX() + i < 8 && from.getY() + i < 8) {
            to = new Position( from.getX() + i, from.getY() + i );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }
        i = 1;
        while (from.getX() + i < 8 && from.getY() - i > -1) {
            to = new Position( from.getX() + i, from.getY() - i );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }
        i = 1;
        while (from.getX() - i > -1 && from.getY() + i < 8) {
            to = new Position( from.getX() - i, from.getY() + i );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }
        i = 1;
        while (from.getX() - i > -1 && from.getY() - i > -1) {
            to = new Position( from.getX() - i, from.getY() - i );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }

        return move;
    }

    @Override
    public BishopPiece clone(Colors player) {
        return new BishopPiece( new Position( position.getX(), position.getY() ), player );
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
