package chess;

import boardgame.Application;
import boardgame.Colors;
import boardgame.Player;
import boardgame.TwoPlayerGame;

import java.util.HashSet;

public class ChessGameplay extends TwoPlayerGame {
    ChessBoard board;

    public ChessGameplay(Player player1, Player player2) {
        super( player1, player2 );
        board = initBoard( 8 );
    }

    @Override
    protected void menu() {
        System.out.println( "\t\t ----------------------" );
        System.out.println( "\t\t|        Chess        |" );
        System.out.println( "\t\t ----------------------" );
        System.out.println( "Rules:" );
        System.out.println( "1. Enter your moves in algebraic notation" );
        System.out.println( "2. Put the other player in checkmate" );
        System.out.println( "3. Type 'help' to get a list of available moves" );
        System.out.println( "4. Type 'quit' or 'exit' to exit the game.\n" );
    }

    ChessBoard initBoard(int size) {
        ChessBoard new_board = new ChessBoard( size );
        new_board.init();
        return new_board;
    }

    @Override
    public void run() {
        ChessBoard testBoard;
        testBoard = board.clone();
        HashSet<ChessMove> initialMoves;

        menu();
        Boolean inCheck = false;
        while (true) {
            board.print();
            initialMoves = board.generateMoves( currentPlayer );
            initialMoves = testBoard.removeBadMoves( initialMoves, currentPlayer );
            if (inCheck) {

                if (initialMoves.size() == 0) {
                    System.out.print( "Checkmate!" );
                    playerWins( otherPlayer );
                    break;
                }
                System.out.println( "Check" );
            }
            while (true) {
                playerPrompt( currentPlayer );
                String input = Application.input.nextLine();
                if (input.equalsIgnoreCase( "exit" ) || input.equalsIgnoreCase( "quit" ))
                    exitGame();
                if (input.equalsIgnoreCase( "help" )) {
                    board.printAvailableMoves( initialMoves );
                    continue;
                }
                if (board.tryPlayingPosition( currentPlayer, input )) {
                    break;
                }
            }
            nextPlayer();
            System.out.println();
            stalemate();

            if (board.playerInCheck( otherPlayer )) {
                inCheck = true;
            } else {
                inCheck = false;
            }
        }
    }

    private void playerWins(Player player) {
        System.out.println( "\nCongratulations, " +
                (player.getName().equalsIgnoreCase( "player 1" ) ? "Uppercase" : "lowercase")
                + "! You won!\n" );
        System.exit( 0 );
    }


    private void playerPrompt(Player currentPlayer) {
        String tmp = "";
        if (currentPlayer.getColor() == Colors.WHITE)
            tmp = "Lowercase";
        else tmp = "Uppercase";
        System.out.println( currentPlayer.getName() + "'s turn. Your piece is " + tmp );
        System.out.println( "Enter your move in algebraic notation(a6-b5): " );
    }

    public void stalemate() {
        if (board.generateMoves( currentPlayer ).size() == 0 || board.generateMoves( otherPlayer ).size() == 0) {
            System.out.println( "It's a stalemate." );
            System.exit( 0 );
        }
    }

}
