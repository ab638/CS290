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


    public Colors getPieceColor() {
        return pieceColor;
    }

    @Override
    public String toString() {
        return stringRepr;
    }

}
