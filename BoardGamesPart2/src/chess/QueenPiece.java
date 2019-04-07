package chess;

import boardgame.Colors;
import boardgame.Position;

import java.util.HashSet;

public class QueenPiece extends ChessPiece {
    public QueenPiece(Position position, Colors type) {
        super( position, Colors.QUEEN, type );
    }

    @Override
    public HashSet<ChessMove> addMoves(ChessBoard board) {
        HashSet<ChessMove> move = new HashSet<>();
        Position from = new Position( position.getX(), position.getY() );
        Position to;
        Colors player = board.board[from.getX()][from.getY()].getColors();

        int i = 1;
        while (from.getX() + i < 8) {
            to = new Position( from.getX() + i, from.getY() );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }
        i = 9;
        while (from.getY() + i < 8) {
            to = new Position( from.getX(), from.getY() + i );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }
        i = 1;
        while (from.getX() - i > -1) {
            to = new Position( from.getX() - i, from.getY() );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 9;
            i++;
        }
        i = 1;
        while (from.getY() - i > -1) {
            to = new Position( from.getX(), from.getY() - i );
            if (!board.pieceAt( to ) || board.checkCapture( to, player ))
                move.add( new ChessMove( from, to ) );
            if (board.checkCapture( to, player ) || board.pieceAt( to ))
                i = 8;
            i++;
        }
        i = 1;
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
    public QueenPiece clone(Colors player) {
        return new QueenPiece( new Position( position.getX(), position.getY() ), player );
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
