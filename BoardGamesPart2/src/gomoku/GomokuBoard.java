package gomoku;

import boardgame.*;

import java.util.regex.Pattern;

public class GomokuBoard extends Board {
    private int boardSize = 19;          // size of a board
    private int maxMoves = boardSize * boardSize; // maximum moves on a board
    private int moves; // number of moves made on a board
    private Cell[][] boardArray; // cells that make up a board


    public GomokuBoard() {
        setBoardSize( 19 );
        moves = 1;
        boardArray = new Cell[boardSize][boardSize];
        init();
    }

    @Override
    public void print() {
        char ch = 'A';
        System.out.print( "    " );
        for (int i = 0; i < boardSize; i++) {
            System.out.print( ch );
            System.out.print( "  " );
            ch++;
        }
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            if (i < 9)
                System.out.print( " " );
            System.out.print( i + 1 );
            System.out.print( "  " );
            for (int j = 0; j < boardSize; j++) {
                if (boardArray[i][j].hasCellColor( Colors.BLACK ))
                    System.out.print( Application.ANSI_BLACK + boardArray[i][j].toString() + Application.ANSI_RESET );
                else if (boardArray[i][j].hasCellColor( Colors.WHITE ))
                    System.out.print( Application.ANSI_WHITE + boardArray[i][j].toString() + Application.ANSI_RESET );
                else
                    System.out.print( Application.ANSI_RED + boardArray[i][j].toString() + Application.ANSI_RESET );
                System.out.print( "  " );
            }
            System.out.println();
        }

    }

    public void init() {
        for (int row = 0; row < boardSize; row++)
            for (int column = 0; column < boardSize; column++)
                boardArray[row][column] = new Cell( new Position( row, column ) );
    }

    boolean matchedPattern(String positionString) {
        Pattern regex = Pattern.compile( "^[A-Sa-s]\\d{1,19}$" );
        return regex.matcher( positionString ).matches();
    }

    public boolean tryPlayingPosition(GomokuPiece gomokuPiece) {
        return checkPosition( gomokuPiece.getPosition(), gomokuPiece );
    }

    private boolean checkPosition(Position position, GomokuPiece gomokuPiece) {
        if (!withinBounds( position )) {
            System.out.println( "Out of bounds. Try again." );
            return false;
        }
        if (!boardArray[position.getX()][position.getY()].toString().equals( "." )) {
            if (boardArray[position.getX()][position.getY()].toString().equalsIgnoreCase( "w" ))
                System.out.println( "White piece at this position." );
            else
                System.out.println( "Black piece at this position." );
            return false;
        } else {
            boardArray[position.getX()][position.getY()].setColors( gomokuPiece.getColors() );
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
                if (curr.toString().equals( player.getColorShortName() )) {
                    if (row < 15)
                        if (count5( row, 1, col, 0 )) {
                            isWinner = true;
                        }
                    if (col < 15) {
                        if (count5( row, 0, col, 1 )) {
                            isWinner = true;
                        }
                        if (row < 15) {
                            if (count5( row, 1, col, 1 )) {
                                isWinner = true;
                            }
                        }
                    }
                    if (col > 3 && row < 15) {
                        if (count5( row, 1, col, -1 ))
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
            if (!boardArray[row + dRow * i][column + dColumn * i].toString().equals( cells.toString() )) {
                return false;
            }
        }
        return true;

    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
}