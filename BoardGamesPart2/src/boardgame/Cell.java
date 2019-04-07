package boardgame;
/* I realized I went overboard with my original design
    and decided that I could model pieces as extensions of the cell
    class instead of abstract pieces which required a ton of unnecessary
    work around and parsing
 */

import static boardgame.Colors.*;

public class Cell {

    public Position position;
    protected Colors colors;

    public Cell(Position position) {

        this( position, EMPTY );
        clear();
    }

    public Cell(Position position, Colors colors) {
        this.position = position;
        this.colors = colors;
    }

    public Cell() {
        // this is just to get rid of the super error
    }

    private void clear() {
        colors = EMPTY;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(Colors colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        switch (colors) {
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
        return this.colors == cellColor;
    }


    public void setPiece(Cell piece) {
        this.colors = piece.colors;
        this.position = piece.position;
    }

    public Position getPosition() {
        return position;
    }
}