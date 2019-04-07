package boardgame;


public abstract class Board {
    protected int boardSize;
    protected Cell[][] board;

    protected boolean withinBounds(Position position) {
        return position.getX() >= 0 && position.getX() < boardSize &&
                position.getY() >= 0 && position.getY() < boardSize;
    }

    public abstract void print();

    public abstract void init();

    protected void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
}
