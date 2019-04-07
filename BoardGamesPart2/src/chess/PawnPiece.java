package chess;

import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;

public class PawnPiece extends ChessPiece {
    public PawnPiece(Position position, Colors color) {

        super( position, Colors.PAWN, color );
    }


    @Override
    public HashSet<ChessMove> addMoves(ChessBoard board) {
        HashSet<ChessMove> move = new HashSet<>();
        Position from;
        Position midpoint;
        Position to;

        from = new Position( position.getX(), position.getY() );
        Colors player = getColors();

        if (player == Colors.WHITE) {
            if (position.getX() < 7) {
                to = from.plus( new Position( 1, 0 ) );
                if (!board.pieceAt( to )) {
                    move.add( new ChessMove( from, to ) );
                }
            }
            if (position.getX() < 6) {
                midpoint = from.plus( new Position( 1, 0 ) );
                ;
                to = midpoint.plus( new Position( 1, 0 ) );
                if (!board.pieceAt( midpoint ) && !board.pieceAt( to ))
                    move.add( new ChessMove( from, to ) );
            }
            if (position.getX() < 7 && position.getY() < 7) {
                to = from.plus( new Position( 1, 1 ) );
                if (board.pieceAt( to )
                        && board.checkCapture( to, player ))
                    move.add( new ChessMove( from, to ) );
            }
            if (position.getX() < 7 && position.getY() > 0) {
                to = from.plus( new Position( 1, -1 ) );
                if (board.pieceAt( to )
                        && board.checkCapture( to, player ))
                    move.add( new ChessMove( from, to ) );
            }
        } else if (player == Colors.BLACK) {
            if (position.getX() > 0) {
                to = from.plus( new Position( -1, 0 ) );
                if (!board.pieceAt( to )) {
                    move.add( new ChessMove( from, to ) );
                }
            }
            if (position.getX() > 1) {
                midpoint = from.plus( new Position( -1, 0 ) );
                to = from.plus( new Position( -2, 0 ) );
                if (!board.pieceAt( midpoint ) && !board.pieceAt( to ))
                    move.add( new ChessMove( from, to ) );
            }
            if (position.getX() > 0 && position.getY() < 7) {
                to = from.plus( new Position( -1, +1 ) );
                if (board.pieceAt( to ) &&
                        board.checkCapture( to, player ))
                    move.add( new ChessMove( from, to ) );
            }
            if (position.getX() > 0 && position.getY() > 0) {
                to = from.plus( new Position( -1, -1 ) );
                if (board.pieceAt( to ) && board.checkCapture( to, player ))
                    move.add( new ChessMove( from, to ) );
            }
        }

        return move;
    }

    @Override
    public PawnPiece clone(Colors player) {
        return new PawnPiece( new Position( position.getX(), position.getY() ), player );
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean checkPawn() {
        if (position.getX() == 0 || position.getX() == 7)
            return true;
        return false;
    }
}
