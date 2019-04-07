package chess;

import boardgame.Move;
import boardgame.Position;

public class ChessMove extends Move {

    private Boolean castle;

    public ChessMove(Position from, Position to) {
        super( from, to );
        castle = false;
    }

    public ChessMove(Position from, Position to, Boolean castle) {
        super( from, to );
        this.castle = castle;
    }

    public Boolean getCastle() {
        return castle;
    }

    public void setCastle(Boolean castle) {
        this.castle = castle;
    }
}
