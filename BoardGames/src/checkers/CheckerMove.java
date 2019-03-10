package checkers;

import boardgame.Position;

public class CheckerMove {

    protected Position from;
    protected Position to;
    protected boolean jump;

    public CheckerMove() {
        this( new Position( 0, 0 ), new Position( 0, 0 ), false );
    }

    public CheckerMove(Position from, Position to, boolean jump) {
        this.from = from;
        this.to = to;
        this.jump = jump;
    }

    boolean equals(CheckerMove x) {
        return (this.from.getX() == x.from.getX() && this.to.getX() == x.to.getX()
                && this.from.getY() == x.from.getY() && this.to.getY() == x.to.getY());
    }

    public String toString() {
        char fromChar;
        char toChar;

        fromChar = (char) (from.getY() + 97);
        toChar = (char) (to.getY() + 97);
        String o = String.valueOf( fromChar ) +
                (from.getX() + 1) +
                "-" +
                toChar +
                (to.getX() + 1);
        return o;
    }
}
