package gomoku;

import boardgame.Colors;
import boardgame.Position;

public class NullGomokuPiece extends GomokuPiece {
    public NullGomokuPiece(Position position) {
        super( position, Colors.EMPTY );
    }
}
