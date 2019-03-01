package boardgame;

public class Position {
    protected final int x;
    protected final int y;

    public Position() {
        this(0, 0);
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


    @Override
    public String toString() {
        return String.format("%1c - %2d", (char) (65 + getX()), getY());
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
