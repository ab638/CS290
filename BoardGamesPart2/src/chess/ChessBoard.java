
package chess;

import boardgame.*;

import java.util.HashSet;

import static boardgame.Application.*;

public class ChessBoard extends Board implements Cloneable {

    private final String positionRegex = "[A-Ha-h][1-8]";
    ChessPiece[][] board;
    AlgebraicNotationParser parser;

    public ChessBoard(int size) {
        board = new ChessPiece[size][size];
        setBoardSize( size );
        initEmpty();
        init();
    }

    public ChessBoard(ChessPiece[][] board) {
        ChessPiece[][] tmp = new ChessPiece[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                tmp[i][j] = board[i][j];
    }

    public HashSet<ChessMove> generateMoves(Player player) {
        HashSet<ChessMove> moves = new HashSet<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j].hasCellColor( player.getColor() )) {
                    moves.addAll( this.board[i][j].addMoves( this ) );
                }
            }
        }
        return moves;
    }

    public boolean tryPlayingPosition(Player player, String input) {
        parser = new AlgebraicNotationParser( positionRegex, input );
        if (!parser.isValidMoveString( input )) {
            System.out.println( "Invalid move. Try Again." );
            return false;
        }

        String[] strings = input.split( "-" );
        Position from = parser.parsePosition( strings[0] );
        Position to = parser.parsePosition( strings[1] );
        return checkPosition( player, from, to );
    }

    private boolean checkPosition(Player player, Position pos1, Position pos2) {
        if (!withinBounds( pos1 ) || !withinBounds( pos2 )) {
            System.out.println( "Out of bounds. Try again." );
            return false;
        } else {
            ChessMove move = new ChessMove( pos1, pos2 );

            if (move( player, move )) {
                checkPawns();
                return true;
            }
        }
        return false;
    }

    private boolean move(Player player, ChessMove move) {
        HashSet<ChessMove> moves;
        ChessBoard test = this.clone();
        Position in = new Position( move.getFrom().getX(), move.getFrom().getY() );
        Position out = new Position( move.getTo().getX(), move.getTo().getY() );
        moves = generateMoves( player );
        moves = test.removeBadMoves( moves, player );
        if (hasMove( moves, move )) {
            if (move.getCastle()) {
                if (move.getTo().getY() - move.getFrom().getY() == 2) {
                    move( player, new ChessMove( move.getFrom(), move.getTo() ) );
                    move( player, new ChessMove( new Position( move.getFrom().getX(), move.getTo().getY() - 2 ),
                            new Position( move.getFrom().getX(), move.getTo().getY() ) ) );
                } else {
                    if (move.getTo().getY() - move.getFrom().getY() == -2) {
                        move( player, new ChessMove( move.getFrom(), move.getTo() ) );
                        move( player, new ChessMove( new Position( move.getFrom().getX(), move.getTo().getY() - 2 ),
                                new Position( move.getFrom().getX(), move.getTo().getY() ) ) );
                    }
                }
            } else {
                if (player.getColor().equals( Colors.BLACK )) {
                    ChessPiece tmp = board[in.getX()][in.getY()];
                    board[in.getX()][in.getY()] = createPiece( genCell( in.getX(), in.getY() ) );
                    tmp.setNewLocation( out );
                    board[out.getX()][out.getY()] = tmp;
                    board[out.getX()][out.getY()].setMoved();
                } else if (player.getColor() == Colors.WHITE) {
                    ChessPiece tmp = board[in.getX()][in.getY()];
                    board[in.getX()][in.getY()] = createPiece( genCell( in.getX(), in.getY() ) );
                    tmp.setNewLocation( out );
                    board[out.getX()][out.getY()] = tmp;
                    board[out.getX()][out.getY()].setMoved();
                }
            }
            return true;
        }

        return false;
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
            System.out.print( boardSize - i );
            System.out.print( "  " );
            for (int j = 7; j >= 0; j--) {
                if (board[7 - i][7 - j].toString().equalsIgnoreCase( "_" ))
                    System.out.print( ANSI_BLUE + board[7 - i][7 - j].toString() + ANSI_RESET );
                else if (board[7 - i][7 - j].toString().equalsIgnoreCase( "#" ))
                    System.out.print( ANSI_RED + board[7 - i][7 - j].toString() + ANSI_RESET );
                else {
                    System.out.print( board[7 - i][7 - j].toString() );
                }
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

    private boolean hasMove(HashSet<ChessMove> moves, ChessMove move) {
        for (ChessMove each : moves) {
            if (move.equals( each )) {
                return true;
            }
        }
        return false;
    }

    public boolean pieceAt(Position position) {
        if (board[position.getX()][position.getY()].hasCellColor( Colors.WHITE )
                || board[position.getX()][position.getY()].hasCellColor( Colors.BLACK ))
            return true;
        return false;
    }

    public boolean checkCapture(Position position, Colors playerColor) {
        if (playerColor.equals( Colors.BLACK ))
            return whitePieceAt( position );
        else if (playerColor.equals( Colors.WHITE ))
            return blackPieceAt( position );
        return false;
    }

    public boolean movedPieceAt(Position position) {
        boolean moved = board[position.getX()][position.getY()].hasMoved();
        if (moved)
            return true;
        return false;
    }

    @Override
    public void init() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                board[row][column] = genCell( row, column );
                if (row == 1 || row == 6) {
                    if (row == 6)
                        board[row][column] =
                                new PawnPiece( new Position( row, column ), Colors.BLACK );
                    else if (row == 1)
                        board[row][column] =
                                new PawnPiece( new Position( row, column ), Colors.WHITE );
                }
                if (row == 0 || row == 7) {
                    if (column == 0 || column == 7)
                        board[row][column] =
                                new RookPiece( new Position( row, column ), (row == 0) ? Colors.WHITE : Colors.BLACK );

                    if (column == 1 || column == 6)
                        board[row][column] =
                                new KnightPiece( new Position( row, column ), (row == 0) ? Colors.WHITE : Colors.BLACK );
                    if (column == 2 || column == 5)
                        board[row][column] =
                                new BishopPiece( new Position( row, column ), (row == 0) ? Colors.WHITE : Colors.BLACK );
                    if (column == 3)
                        board[row][column] =
                                new QueenPiece( new Position( row, column ), (row == 0) ? Colors.WHITE : Colors.BLACK );
                    if (column == 4)
                        board[row][column] =
                                new KingPiece( new Position( row, column ), (row == 0) ? Colors.WHITE : Colors.BLACK );
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

    protected ChessPiece genCell(int row, int col) {
        if ((row + col) % 2 == 0) {
            ChessPiece cell = new EmptyChessPiece( new Position( row, col ), Colors.UNDER );
            return cell;
        }
        ChessPiece cell = new EmptyChessPiece( new Position( row, col ), Colors.POUND );
        return cell;
    }

    public boolean playerInCheck(Player player) {
        HashSet<ChessMove> moves = this.generateMoves( player );
        for (ChessMove each : moves) {
            if (this.checkCapture( each.getTo(), player.getColor() )
                    && this.kingAt( each.getTo() )) {
                return true;
            }
        }
        return false;

    }

    public boolean kingAt(Position to) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].position.equals( to )
                        && board[i][j].isKing()) {
                    return true;
                }
            }
        }

        return false;
    }

    void checkPawns() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].checkPawn())
                    setQueen( board[i][j] );
            }
        }
    }

    private void setQueen(ChessPiece piece) {
        Position position = piece.getPosition();
        board[position.getX()][position.getY()] = new QueenPiece( position, piece.getColors() );
    }

    public void printAvailableMoves(HashSet<ChessMove> move) {
        System.out.println( "Available Moves: " + move );
    }

    @Override
    public ChessBoard clone() {
        return new ChessBoard( board );
    }

    public HashSet<ChessMove> removeBadMoves(HashSet<ChessMove> moves, Player player) {
        HashSet<ChessMove> newMoves = new HashSet<>();
        for (ChessMove each : moves)
            if (testMove( each, player ))
                if (each.getCastle()) {
                    if (!playerInCheck( player ))
                        newMoves.add( each );
                } else newMoves.add( each );
        return newMoves;
    }

    private boolean testMove(ChessMove each, Player player) {
        this.move( player, each );
        return !this.playerInCheck( player );
    }

    private ChessPiece createPiece(ChessPiece piece) {
        ChessPiece newPiece;
        if (piece.pieceName.equals( Colors.PAWN ))
            newPiece = new PawnPiece( piece.getPosition(), piece.getColors() );
        else if (piece.pieceName.equals( Colors.BISHOP ))
            newPiece = new BishopPiece( piece.getPosition(), piece.getColors() );
        else if (piece.pieceName.equals( Colors.ROOK ))
            newPiece = new RookPiece( piece.getPosition(), piece.getColors() );
        else if (piece.pieceName.equals( Colors.QUEEN ))
            newPiece = new QueenPiece( piece.getPosition(), piece.getColors() );
        else if (piece.pieceName.equals( Colors.KING ))
            newPiece = new KingPiece( piece.getPosition(), piece.getColors() );
        else if (piece.pieceName.equals( Colors.KNIGHT ))
            newPiece = new KnightPiece( piece.getPosition(), piece.getColors() );
        else
            newPiece = new EmptyChessPiece( piece.getPosition(),
                    genCell( piece.getPosition().getX(), piece.getPosition().getY() ).getColors() );
        return newPiece;


    }

    public boolean whitePieceAt(Position pos) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j].position.equals( pos ) && board[pos.getX()][pos.getY()].hasCellColor( Colors.WHITE ))
                    return true;
        return false;
    }

    public boolean blackPieceAt(Position pos) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j].position.equals( pos ) && board[pos.getX()][pos.getY()].hasCellColor( Colors.BLACK ))
                    return true;
        return false;
    }
}

