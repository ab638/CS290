package boardgame;


public abstract class Piece {
    protected Colors pieceColor;
    protected String stringRepr;
    protected Position position;

    public enum Colors {
        BLACK, WHITE, EMPTY
    }

    protected Piece() {
        this.position = new Position();
        this.stringRepr = ".";
        this.pieceColor = Colors.EMPTY;
    }

    protected Piece(Colors pieceColor, Position position) {
        this.position = position;
        this.pieceColor = pieceColor;
    }

    public String getStringRepr() {
        return stringRepr;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean matchesColor(Piece piece) {
        return piece.pieceColor == this.pieceColor;
    }

    public boolean matchesColor(Colors c) {
        return this.pieceColor == c;
    }

    public Colors getPieceColor() {
        return pieceColor;
    }

    @Override
    public String toString() {
        return stringRepr;
    }

}
