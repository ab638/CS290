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
            if (i < 9)
                System.out.print( " " );
            System.out.print( i + 1 );
            System.out.print( "  " );
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].toString().equalsIgnoreCase( "b" ))
                    System.out.print( ANSI_BLACK + board[i][j].toString() + ANSI_RESET );
                else if (board[i][j].toString().equalsIgnoreCase( "w" ))
                    System.out.print( ANSI_WHITE + board[i][j].toString() + ANSI_RESET );
                else if (board[i][j].toString().equalsIgnoreCase( "_" ))
                    System.out.print( ANSI_BLUE + board[i][j].toString() + ANSI_RESET );
                else if (board[i][j].toString().equalsIgnoreCase( "#" ))
                    System.out.print( ANSI_RED + board[i][j].toString() + ANSI_RESET );
                System.out.print( "  " );
            }
            System.out.print( i + 1 );
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
                    board[row][column].setContent( Colors.WHITE );
                }
                if (row >= boardSize - 3 && board[row][column].hasCellColor( Colors.UNDER )) {
                    board[row][column].setContent( Colors.BLACK );

                }
            }
        }
    }

    private void initEmpty() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                board[row][column] = genCell( row, column );
            }
        }
    }

    private CheckerPiece genCell(int row, int col) {
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
            if (pos2.getX() < pos1.getX() - 1 || pos2.getY() > pos1.getY() + 1)
                move.jump = true;
            else move.jump = false;

            if (this.move( player, move, false )) {
                Vector<CheckerMove> m = new Vector<>();
                m.add( move );
                if (move.jump && this.secondJump( move )) {
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
            if (each.jump) {
                isJump = true;
            }
        }
        if (isJump) {
            for (CheckerMove each : moves) {
                if (each.jump)
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
            if (move.elementAt( i ).jump) {
                str.append( "[" );
                str.append( move.elementAt( i ) );
                str.append( "]" );
                jumpCase = true;
            }
            i++;
        }

        if (jumpCase == true)
            System.out.println( "Jump Available: " + str.toString() );
        else System.out.println( "Available Moves: " + move );
    }

    Vector<CheckerMove> generateMoves(Player player) {
        Vector<CheckerMove> moves = new Vector<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].hasCellColor( player.getColor() ))
                    moves.addAll( board[i][j].addMoves( this ) );
            }
        }

        return moves;

    }

    public boolean move(Player player, CheckerMove move, boolean secondJump) {
        Vector<CheckerMove> moves;

        Position in = new Position( move.from.getX(), move.from.getY() );
        Position out = new Position( move.to.getX(), move.to.getY() );
        CheckerPiece inCell = board[in.getX()][in.getY()];
        boolean isKing = inCell.isKing();
        if (secondJump) {
            moves = inCell.addMoves( this );
            moves = checkJumps( moves );
        } else moves = this.generateMoves( player );

        if (hasMove( moves, move )) {
            if (move.jump) {
                if (player.getColor().equals( Colors.BLACK ))
                    this.board[out.getX()][out.getY()] = new CheckerPiece( out, Colors.BLACK, isKing );
                else this.board[out.getX()][out.getY()] = new CheckerPiece( out, Colors.WHITE, isKing );


                this.board[in.getX()][in.getY()] = new CheckerPiece( out, Colors.UNDER );

                this.board[in.getX() + (out.getX() - in.getX()) / 2][in.getY() + (out.getY() - in.getY()) / 2] = new CheckerPiece( new Position( in.getX() + (out.getX() - in.getX()) / 2, in.getY() + (out.getY() - in.getY()) / 2 ), Colors.UNDER );


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
        Vector<CheckerMove> moves = board[move.to.getX()][move.to.getY()].addMoves( this );
        for (CheckerMove each : moves)
            if (each.jump)
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
