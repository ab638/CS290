package checkers;

import boardgame.Move;
import boardgame.Position;

public class CheckerMove extends Move {
    protected boolean jump;


    public CheckerMove(Position from, Position to, boolean jump) {
        super( from, to );
        this.jump = jump;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
