package gomoku;

import boardgame.Board;
import boardgame.Cell;
import boardgame.Player;
import boardgame.Position;

import java.util.regex.Pattern;

public class GomokuBoard extends Board {
    private int boardSize = 19;          // size of a board
    private int maxMoves = boardSize * boardSize; // maximum moves on a board
    private int moves; // number of moves made on a board
    private Cell[][] boardArray; // cells that make up a board


    public GomokuBoard() {
        super(19);
        moves = 1;
        boardArray = new Cell[boardSize][boardSize];
        init();
    }

    @Override
    public void print() {
        char ch = 'A';
        System.out.print("    ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(ch);
            System.out.print("  ");
            ch++;
        }
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            if (i < 9)
                System.out.print(" ");
            System.out.print(i + 1);
            System.out.print("  ");
            for (int j = 0; j < boardSize; j++) {
                System.out.print(boardArray[i][j].toString());
                System.out.print("  ");
            }
            System.out.println();
        }

    }

    public void init() {
        for (int row = 0; row < boardSize; row++)
            for (int column = 0; column < boardSize; column++)
                boardArray[row][column] = new Cell(new Position(row, column));
    }

    private boolean matchedPattern(String positionString) {
        Pattern regex = Pattern.compile("[A-Za-z]-\\d{1,19}");
        return regex.matcher(positionString).matches();
    }

    public boolean tryPlayingPosition(String input, GomokuPiece gomokuPiece) {
        String[] split = input.split("-", 2);
        if (!matchedPattern(input)) {
            System.out.println("Bad format.");
            return false;
        }
        int column = Integer.valueOf(split[0].toUpperCase().charAt(0)) - 65; // gets back to 0
        int row = 0;
        try {
            row = Integer.parseInt(split[1]);
            Position position = new Position(row - 1, column);
            return checkPosition(position, gomokuPiece);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Formatting incorrect. Try again.");
            return false;
        }
    }

    private boolean checkPosition(Position position, GomokuPiece gomokuPiece) {
        if (!withinBounds(position)) {
            System.out.println("Out of bounds. Try again.");
            return false;
        }
        if (boardArray[position.getX()][position.getY()].toString() != ".") {
            if (boardArray[position.getX()][position.getY()].toString().equalsIgnoreCase("w"))
                System.out.println("White piece at this position.");
            else
                System.out.println("Black piece at this position.");
            return false;
        } else {
            boardArray[position.getX()][position.getY()].setContent(gomokuPiece.getPieceColor());
            return true;
        }
    }

    public boolean isDraw() {
        return currMoves() == maxMoves;
    }

    private int currMoves() {
        int currMoves = moves;
        return currMoves;
    }

    public boolean checkWin(Player player) {
        boolean isWinner = false;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Cell curr = boardArray[row][col];
                if (curr.toString() == player.getColorShortName()) {
                    if (row < 15)
                        if (count5(row, 1, col, 0)) {
                            isWinner = true;
                        }
                    if (col < 15) {
                        if (count5(row, 0, col, 1)) {
                            isWinner = true;
                        }
                        if (row < 15) {
                            if (count5(row, 1, col, 1)) {
                                isWinner = true;
                            }
                        }
                    }
                    if (col > 3 && row < 15) {
                        if (count5(row, 1, col, -1))
                            isWinner = true;
                    }


                }
            }
        }
        return isWinner;
    }

    private boolean count5(int row, int dRow, int column, int dColumn) {
        Cell cells = boardArray[row][column];
        for (int i = 1; i < 5; i++) {
            if (boardArray[row + dRow * i][column + dColumn * i].toString() != cells.toString()) {
                return false;
            }
        }
        return true;

    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
}