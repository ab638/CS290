package chess;

import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;

public class KingPiece extends ChessPiece {
    public KingPiece(Position position, Colors type) {
        super( position, Colors.KING, type );
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public HashSet<ChessMove> addMoves(ChessBoard board) {
        HashSet<ChessMove> move = new HashSet<>();
        Position from = new Position( position.getX(), position.getY() );
        Position to;
        Colors player = board.board[from.getX()][from.getY()].getColors();
        if (from.getX() + 1 < 8 && from.getY() + 1 < 8) {
            to = new Position( from.getX() + 1, from.getY() + 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() - 1 > -1 && from.getY() + 1 < 8) {
            to = new Position( from.getX() - 1, from.getY() + 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() - 1 > -1 && from.getY() - 1 > -1) {
            to = new Position( from.getX() - 1, from.getY() - 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() + 1 < 8 && from.getY() - 1 < -1) {
            to = new Position( from.getX() + 1, from.getY() - 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() + 1 < 8) {
            to = new Position( from.getX() + 1, from.getY() );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getY() + 1 < 8) {
            to = new Position( from.getX(), from.getY() + 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getX() - 1 > -1) {
            to = new Position( from.getX() - 1, from.getY() );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        if (from.getY() - 1 > -1) {
            to = new Position( from.getX(), from.getY() - 1 );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
        }
        // castling
        if (!moved
                && board.pieceAt( new Position( position.getX(), position.getY() + 2 ) )
                && !board.pieceAt( new Position( position.getX(), position.getY() + 1 ) )
                && !board.pieceAt( new Position( position.getX(), position.getY() ) )
                && !board.movedPieceAt( new Position( position.getX(), position.getY() + 1 ) )) {
            move.add( new ChessMove( from, new Position( from.getX(), from.getY() + 1 ), true ) );
        }
        if (!moved
                && board.pieceAt( new Position( position.getX(), position.getY() - 3 ) )
                && !board.pieceAt( new Position( position.getX(), position.getY() - 2 ) )
                && !board.pieceAt( new Position( position.getX(), position.getY() - 1 ) )
                && !board.pieceAt( new Position( position.getX(), position.getY() ) )
                && !board.movedPieceAt( new Position( position.getX(), position.getY() - 3 ) )) {
            move.add( new ChessMove( from, new Position( from.getX(), from.getY() - 1 ), true ) );
        }

        return move;
    }

    @Override
    public KingPiece clone(Colors player) {
        return new KingPiece( new Position( position.getX(), position.getY() ), player );
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
