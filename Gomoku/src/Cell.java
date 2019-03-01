
public class Cell {
    private char symbol;
    private Colors content;
    private int row, col;

    public Cell(Position position) {

        this(position, Colors.EMPTY);
        clear();
    }

    public Cell(Position position, Colors content) {

        this.row = position.getX();
        this.col = position.getY();
        this.content = content;
    }

    public void clear() {
        content = Colors.EMPTY;
    }

    public void setContent(Colors content) {
        this.content = content;
    }

    public Colors getContent() {
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