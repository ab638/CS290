package gomoku;

import boardgame.Cell;
import boardgame.Colors;
import boardgame.Position;
/* I realized I went overboard with my original design
    and decided that I could model pieces as extensions of the cell
    class instead of abstract pieces which required a ton of unnecessary
    work around and parsing
 */

public class GomokuPiece extends Cell {

    public GomokuPiece() {
        super( new Position( 0, 0 ), Colors.EMPTY );
    }

    public GomokuPiece(Position position, Colors content) {
        super( position, content );
    }

    public GomokuPiece createPiece(Position position, Colors content) {
        if (content == Colors.BLACK)
            return new BlackGomokuPiece( position );
        else if (content == Colors.WHITE)
            return new WhiteGomokuPiece( position );

        return new NullGomokuPiece( position );
    }


}
