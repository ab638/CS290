package boardgame;

public abstract class Board {
    protected int boardSize;
    protected Cell[][] board;

    public Board(int size) {
        this.boardSize = size;
        board = new Cell[size][size];
    }

    public Cell cellAt(Position position) {
        return board[position.getX()][position.getY()];
    }

    public void setCell(Position position, Cell cell) {
        board[position.getX()][position.getY()] = cell;
    }

    public boolean withinBounds(Position position) {
        return position.getX() >= 0 && position.getX() < boardSize &&
                position.getY() >= 0 && position.getY() < boardSize;
    }

    public void clearCell(Position position) {
        cellAt(position).clear();
    }

    public abstract void print();

    public abstract void init();

}
