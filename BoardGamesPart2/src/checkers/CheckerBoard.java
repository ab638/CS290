package checkers;

import boardgame.*;

import java.util.Vector;

import static boardgame.Application.*;

public class CheckerBoard extends Board {
    private final String positionRegex = "[A-Ha-h][1-8]";

    CheckerPiece[][] board;
    AlgebraicNotationParser parser;

    public CheckerBoard(int size) {
        board = new CheckerPiece[size][size];
        setBoardSize( size );
        initEmpty();
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

            System.out.print( boardSize - i );
            System.out.print( "  " );
            for (int j = 7; j >= 0; j--) {
                if (board[7 - i][7 - j].toString().equalsIgnoreCase( "b" ))
                    System.out.print( ANSI_BLACK + board[7 - i][7 - j].toString() + ANSI_RESET );
                else if (board[7 - i][7 - j].toString().equalsIgnoreCase( "w" ))
                    System.out.print( ANSI_WHITE + board[7 - i][7 - j].toString() + ANSI_RESET );
                else if (board[7 - i][7 - j].toString().equalsIgnoreCase( "_" ))
                    System.out.print( ANSI_BLUE + board[7 - i][7 - j].toString() + ANSI_RESET );
                else if (board[7 - i][7 - j].toString().equalsIgnoreCase( "#" ))
                    System.out.print( ANSI_RED + board[7 - i][7 - j].toString() + ANSI_RESET );
                System.out.print( "  " );
            }
            System.out.print( boardSize - i );
            System.out.println();
        }
        ch = 'A';
        System.out.print( "    " );
        for (int i = 0; i < boardSize; i++) {
            System.out.print( ch );
            System.out.print( "  " );
            ch++;
        }
        System.out.println();
    }

    @Override
    public void init() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                board[row][column] = genCell( row, column );
                if (row < 3 && board[row][column].hasCellColor( Colors.UNDER )) {
                    board[row][column].setColors( Colors.WHITE );
                }
                if (row >= boardSize - 3 && board[row][column].hasCellColor( Colors.UNDER )) {
                    board[row][column].setColors( Colors.BLACK );
                }
            }
        }
    }

    private void initEmpty() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                this.board[row][column] = genCell( row, column );
            }
        }
    }

    protected CheckerPiece genCell(int row, int col) {
        if ((row + col) % 2 == 0) {
            CheckerPiece cell = new CheckerPiece( new Position( row, col ), Colors.UNDER );
            return cell;
        }
        CheckerPiece cell = new CheckerPiece( new Position( row, col ), Colors.POUND );
        return cell;
    }

    public boolean tryPlayingPosition(Player player, String input, CheckerPiece piece) {
        parser = new AlgebraicNotationParser( positionRegex, input );
        if (!parser.isValidMoveString( input )) {
            System.out.println( "Invalid move. Try Again." );
            return false;
        }

        String[] strings = input.split( "-" );
        Position from = parser.parsePosition( strings[0] );
        Position to = parser.parsePosition( strings[1] );

        return checkPosition( player, from, to, piece );
    }

    private boolean checkPosition(Player player, Position pos1, Position pos2, CheckerPiece checkerPiece) {
        if (!withinBounds( pos1 ) || !withinBounds( pos2 )) {
            System.out.println( "Out of bounds. Try again." );
            return false;
        }
        if (board[pos2.getX()][pos2.getY()].toString().equals( "#" )) {
            System.out.println( "You can't place a piece here." );
            return false;
        } else {

            CheckerMove move = new CheckerMove( pos1, pos2, false );
            if (Math.abs( pos1.getX() - pos2.getX() ) == 2 && Math.abs( pos1.getY() - pos2.getY() ) == 2)
                move.setJump( true );
            else move.setJump( false );

            if (move( player, move, false )) {
                Vector<CheckerMove> m = new Vector<>();
                m.add( move );
                if (move.isJump() && this.secondJump( move )) {
                    print();
                    printAvailableMoves( m );
                    moveAgain( player, pos1, pos2 );
                }
                return true;
            }
        }
        return false;
    }

    private void moveAgain(Player player, Position pos1, Position pos2) {
        CheckerMove move = new CheckerMove( pos1, pos2, false );
        Vector<CheckerMove> m = new Vector<>();
        m.add( move );

        while (true) {
            print();
            printAvailableMoves( m );
            System.out.println( "Another jump is available, go again: " );
            String str = Application.input.nextLine();
            if (tryPlayingPosition( player, str, new CheckerPiece( new Position(), player.getColor(), false ) ))
                return;
        }
    }

    private boolean hasMove(Vector<CheckerMove> moves, CheckerMove move) {
        moves = checkJumps( moves );
        for (CheckerMove each : moves) {
            if (move.equals( each )) {
                return true;
            }
        }
        return false;
    }

    private Vector<CheckerMove> checkJumps(Vector<CheckerMove> moves) {
        Vector<CheckerMove> newMoves = new Vector<>();
        boolean isJump = false;

        for (CheckerMove each : moves) {
            if (each.isJump()) {
                isJump = true;
            }
        }
        if (isJump) {
            for (CheckerMove each : moves) {
                if (each.isJump())
                    newMoves.add( each );
            }
        } else newMoves = moves;
        return newMoves;
    }

    public void printAvailableMoves(Vector<CheckerMove> move) {
        StringBuilder str = new StringBuilder();
        boolean jumpCase = false;
        int i = 0;
        for (CheckerMove each : move) {
            if (move.elementAt( i ).isJump()) {
                str.append( "[" );
                str.append( move.elementAt( i ) );
                str.append( "]" );
                jumpCase = true;
            }
            i++;
        }

        if (jumpCase == true) {
            System.out.println( "Jump Available: " + str.toString() );
        } else System.out.println( "Available Moves: " + move );
    }

    Vector<CheckerMove> generateMoves(Player player) {
        Vector<CheckerMove> moves = new Vector<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j].hasCellColor( player.getColor() ))
                    moves.addAll( board[i][j].addMoves( this ) );
            }
        }

        return moves;

    }

    public boolean move(Player player, CheckerMove move, boolean secondJump) {
        Vector<CheckerMove> moves;

        Position in = new Position( move.getFrom().getX(), move.getFrom().getY() );
        Position out = new Position( move.getTo().getX(), move.getTo().getY() );
        Position midpoint = new Position( (in.getX() + out.getX()) / 2, (in.getY() + out.getY()) / 2 );

        boolean isKing = board[in.getX()][in.getY()].isKing();
        if (secondJump) {
            moves = board[in.getX()][in.getY()].addMoves( this );
            moves = checkJumps( moves );
        } else
            moves = generateMoves( player );

        if (hasMove( moves, move )) {
            if (move.isJump()) {
                if (player.getColor().equals( Colors.BLACK ))
                    this.board[out.getX()][out.getY()] = new CheckerPiece( out, Colors.BLACK, isKing );
                else if (player.getColor().equals( Colors.WHITE ))
                    this.board[out.getX()][out.getY()] = new CheckerPiece( out, Colors.WHITE, isKing );

                this.board[midpoint.getX()][midpoint.getY()] =
                        new NullCheckerPiece( midpoint, Colors.UNDER, false );

                this.board[in.getX()][in.getY()] = new NullCheckerPiece( out, Colors.UNDER, false );

            } else {
                if (player.getColor().equals( Colors.BLACK ))
                    this.board[out.getX()][out.getY()] = new CheckerPiece( out, Colors.BLACK, isKing );
                else this.board[out.getX()][out.getY()] = new CheckerPiece( out, Colors.WHITE, isKing );

                this.board[in.getX()][in.getY()] = new CheckerPiece( in, Colors.UNDER );
            }
            this.board[out.getX()][out.getY()].checkKing();

            return true;
        } else {
            return false;
        }
    }

    public boolean secondJump(CheckerMove move) {
        Vector<CheckerMove> moves = board[move.getTo().getX()][move.getTo().getY()].addMoves( this );
        for (CheckerMove each : moves)
            if (each.isJump())
                return true;
        return false;
    }

    public boolean checkWin(Player currentPlayer) {
        boolean win = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!board[i][j].hasCellColor( Colors.UNDER ) && !board[i][j].hasCellColor( currentPlayer.getColor() ))
                    win = false;
            }
        }
        return win;
    }

}
