package checkers;
/* I realized I went overboard with my original design
    and decided that I could model pieces as extensions of the cell
    class instead of abstract pieces which required a ton of unnecessary
    work around and parsing
 */

import boardgame.Cell;
import boardgame.Colors;
import boardgame.Position;

import java.util.Vector;

public class CheckerPiece extends Cell {

    protected boolean isKing;


    public CheckerPiece(Position position, Colors content, boolean isKing) {
        super( position, content );
        this.isKing = isKing;
    }

    public CheckerPiece(Position position, Colors under) {
        super( position, under );
        this.isKing = false;
    }

    public CheckerPiece() {
        super( new Position( 0, 0 ), Colors.EMPTY );
    }

    boolean isKing() {
        return isKing;
    }

    public String toString() {
        if (isKing)
            return super.toString().toUpperCase();
        else
            return super.toString();
    }

    public void setKing() {
        isKing = true;
    }

    public void checkKing() {
        if (this.content.toString().equalsIgnoreCase( "black" )) {
            if (this.position.getX() == 0)
                this.setKing();
        } else {
            if (this.position.getX() == 7)
                this.setKing();
        }
    }

    public CheckerPiece createCheckerPiece(Position position, Colors colors, boolean isKing) {
        if (colors.equals( Colors.BLACK ))
            return new BlackCheckerPiece( position, colors, isKing );
        else if (colors.equals( Colors.WHITE ))
            return new BlackCheckerPiece( position, colors, isKing );
        return new NullCheckerPiece( position, colors, isKing );
    }

    public Vector<CheckerMove> addMoves(CheckerBoard board) {
        Vector<CheckerMove> moves = new Vector<>();
        Position from;
        Position to;
        Position inTheWay;
        from = new Position( position.getX(), position.getY() );
        Colors cellContent = board.board[from.getX()][from.getY()].content;

        if (cellContent == Colors.UNDER) {
            return moves;
        }
        if (this.isKing || cellContent == Colors.BLACK) {
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
        if (this.isKing || cellContent == Colors.WHITE) {
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
