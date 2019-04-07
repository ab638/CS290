package boardgame;

public class Position {
    private int x;
    private int y;

    public Position() {
        this( 0, 0 );
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position plus(Position that) {
        return new Position( this.getX() + that.getX(), this.getY() + that.getY() );
    }

    @Override
    public String toString() {
        return String.format( "%c%d", (char) ('A' + getY()), getX() + 1 );
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position))
            return false;
        if (obj == this)
            return true;

        Position that = (Position) obj;
        return this.getY() == that.getY() && this.getX() == that.getX();
    }
}
