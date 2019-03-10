package checkers;

import boardgame.Colors;
import boardgame.Position;

import java.util.Vector;

public class NullCheckerPiece extends CheckerPiece {
    public NullCheckerPiece(Position position, Colors content, boolean isKing) {
        super( position, content, isKing );
    }

    public Vector<CheckerMove> addMoves(CheckerBoard board) {
        Vector<CheckerMove> moves = new Vector<>();
        Position from;
        from = new Position( position.getX(), position.getY() );
        Colors cellContent = board.board[from.getX()][from.getY()].getContent();

        if (cellContent == Colors.UNDER) {
            return moves;
        }


        return moves;
    }
}
