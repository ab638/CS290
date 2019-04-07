package chess;

import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;

public class KnightPiece extends ChessPiece {
    public KnightPiece(Position position, Colors color) {
        super( position, Colors.KNIGHT, color );

    }

    @Override
    public HashSet<ChessMove> addMoves(ChessBoard board) {
        HashSet<ChessMove> move = new HashSet<>();
        Position from;
        Position to;

        from = new Position( position.getX(), position.getY() );
        Colors player = board.board[from.getX()][from.getY()].getColors();
        if (from.getX() > 1 && from.getY() < 7) {
            to = new Position( from.getX() - 2, from.getY() + 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() > 0 && from.getY() < 6) {
            to = new Position( from.getX() - 1, from.getY() + 2 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() < 7 && from.getY() < 6) {
            to = new Position( from.getX() + 1, from.getY() + 2 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() < 6 && from.getY() < 7) {
            to = new Position( from.getX() + 2, from.getY() + 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() < 6 && from.getY() > 0) {
            to = new Position( from.getX() + 2, from.getY() - 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() < 7 && from.getY() > 1) {
            to = new Position( from.getX() + 1, from.getY() - 2 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() > 0 && from.getY() > 1) {
            to = new Position( from.getX() - 1, from.getY() - 2 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() > 1 && from.getY() > 0) {
            to = new Position( from.getX() - 2, from.getY() - 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }


        return move;
    }

    @Override
    public KnightPiece clone(Colors player) {
        return new KnightPiece( new Position( position.getX(), position.getY() ), player );
    }

    @Override
    public boolean isNull() {
        return false;
    }


}
