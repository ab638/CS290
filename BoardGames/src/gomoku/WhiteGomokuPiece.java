package gomoku;

import boardgame.Colors;
import boardgame.Position;

public class WhiteGomokuPiece extends GomokuPiece {
    public WhiteGomokuPiece(Position position) {
        super( position, Colors.WHITE );
    }
}
