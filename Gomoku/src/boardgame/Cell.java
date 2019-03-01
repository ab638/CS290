package boardgame;


import static boardgame.Piece.Colors.EMPTY;

public class Cell {
    private char symbol = '.';
    private Piece.Colors content;
    private int row, col;

    public Cell(Position position) {

        this(position, EMPTY);
        clear();
    }

    public Cell(Position position, Piece.Colors content) {

        this.row = position.getX();
        this.col = position.getY();
        this.content = content;
    }

    public void clear() {
        content = EMPTY;
    }

    public void setContent(Piece.Colors content) {
        this.content = content;
    }

    public Piece.Colors getContent() {
        return content;
    }

    @Override
    public String toString() {
        switch (content) {
            case BLACK:
                return "B";
            case WHITE:
                return "W";
            case EMPTY:
                return ".";
        }
        return null;
    }
}