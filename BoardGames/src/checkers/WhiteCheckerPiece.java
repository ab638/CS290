package checkers;

import boardgame.Colors;
import boardgame.Position;

import java.util.Vector;

public class WhiteCheckerPiece extends CheckerPiece {
    public WhiteCheckerPiece(Position position, Colors content, boolean isKing) {
        super( position, content, isKing );
    }

    public Vector<CheckerMove> addMoves(CheckerBoard board) {
        Vector<CheckerMove> moves = new Vector<>();
        Position from;
        Position to;
        Position inTheWay;
        from = new Position( position.getX(), position.getY() );
        Colors cellContent = board.board[from.getX()][from.getY()].getContent();

        if (cellContent == Colors.UNDER) {
            return moves;
        }
        if (this.isKing) {
            to = new Position( from.getX() - 1, from.getY() - 1 );
            if (from.getY() > 0 && from.getX() > 0 && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, false ) );

            }
            to = new Position( from.getX() - 1, from.getY() + 1 );
            if (from.getX() > 0 && from.getY() < 7 && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, false ) );

            }
            inTheWay = new Position( from.getX() - 1, from.getY() - 1 );
            to = new Position( from.getX() - 2, from.getY() - 2 );
            if (from.getX() > 1 && from.getY() > 1 && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( cellContent )) && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( Colors.UNDER )) && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, true ) );

            }
            inTheWay = new Position( from.getX() - 1, from.getY() + 1 );
            to = new Position( from.getX() - 2, from.getY() + 2 );
            if (from.getX() > 1 && from.getY() < 6 && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( cellContent )) && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( Colors.UNDER )) && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, true ) );
            }
        }
        if (cellContent == Colors.WHITE) {
            to = new Position( from.getX() + 1, from.getY() + 1 );
            if (from.getX() < 7 && from.getY() < 7 && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, false ) );
            }
            to = new Position( from.getX() + 1, from.getY() - 1 );
            if (from.getX() < 7 && from.getY() > 0 && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, false ) );
            }
            inTheWay = new Position( from.getX() + 1, from.getY() - 1 );
            to = new Position( from.getX() + 2, from.getY() - 2 );
            if (from.getX() < 6 && from.getY() > 1 && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( cellContent )) && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( Colors.UNDER )) && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, true ) );
            }
            inTheWay = new Position( from.getX() + 1, from.getY() + 1 );
            to = new Position( from.getX() + 2, from.getY() + 2 );
            if (from.getX() < 6 && from.getY() < 6 && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( cellContent )) && !(board.board[inTheWay.getX()][inTheWay.getY()].hasCellColor( Colors.UNDER )) && board.board[to.getX()][to.getY()].hasCellColor( Colors.UNDER )) {
                moves.add( new CheckerMove( from, to, true ) );
            }
        }
        return moves;
    }
}
