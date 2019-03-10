package boardgame;
/* I realized I went overboard with my original design
    and decided that I could model pieces as extensions of the cell
    class instead of abstract pieces which required a ton of unnecessary
    work around and parsing
 */

import static boardgame.Colors.*;

public class Cell {

    protected Colors content;
    protected Position position;

    public Cell(Position position) {

        this( position, EMPTY );
        clear();
    }

    protected Cell(Position position, Colors content) {
        this.position = position;
        this.content = content;
    }

    private void clear() {
        content = EMPTY;
    }

    public Colors getContent() {
        return content;
    }

    public void setContent(Colors content) {
        this.content = content;
    }

    @Override
    public String toString() {
        switch (content) {
            case BLACK:
                return "b";
            case WHITE:
                return "w";
            case EMPTY:
                return ".";
            case POUND:
                return "#";
            case UNDER:
                return "_";
        }
        return null;
    }


    public boolean isBlank() {
        return (hasCellColor( EMPTY ) || hasCellColor( UNDER ) || hasCellColor( POUND ));
    }

    public boolean hasCellColor(Colors cellColor) {
        return this.content == cellColor;
    }


    public void setPiece(Cell piece) {
        this.content = piece.content;
        this.position = piece.position;
    }

    public Position getPosition() {
        return position;
    }
}