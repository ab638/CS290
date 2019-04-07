package boardgame;

public class Move {

    protected Position from;
    protected Position to;

    public Move() {
        this( new Position( 0, 0 ), new Position( 0, 0 ) );
    }

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public boolean equals(Move x) {
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
