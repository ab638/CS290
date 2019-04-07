package checkers;

import boardgame.Application;
import boardgame.Player;
import boardgame.Position;
import boardgame.TwoPlayerGame;

import java.util.Vector;

public class CheckersGameplay extends TwoPlayerGame {
    CheckerBoard board;


    public CheckersGameplay(Player p1, Player p2) {
        super( p1, p2 );

        this.board = initBoard( 8, p1, p2 );
    }

    CheckerBoard initBoard(int size, Player player1, Player player2) {
        CheckerBoard new_board = new CheckerBoard( size );
        new_board.init();
        return new_board;
    }

    @Override
    public void run() {
        menu();
        while (true) {
            board.print();
            playerTurn( currentPlayer );
            if (playerHasWon( currentPlayer ))
                playerWins( currentPlayer );
            nextPlayer();
        }
    }

    @Override
    protected void menu() {
        System.out.println( "\t\t ----------------------" );
        System.out.println( "\t\t|       Checkers       |" );
        System.out.println( "\t\t ----------------------" );
        System.out.println( "Rules:" );
        System.out.println( "1. Enter your moves in algebraic notation" );
        System.out.println( "2. Eliminate the other players pieces to win" );
        System.out.println( "3. Type 'quit' or 'exit' to exit the game.\n" );
    }

    private void playerWins(Player player) {

        System.out.println( "\nCongratulations, " + player.getName() + "! You won!\n" );
        board.print();
        System.exit( 0 );
    }

    private void playerPrompt(Player currentPlayer) {
        System.out.println( currentPlayer.getName() + "'s turn. Your piece is " + currentPlayer.getColor() );
        System.out.println( "Enter your move in algebraic notation(a6-b5): " );
    }

    private void playerTurn(Player currentPlayer) {
        Vector<CheckerMove> initialMoves;
        CheckerPiece piece = new CheckerPiece();
        while (true) {
            initialMoves = board.generateMoves( currentPlayer );
            board.printAvailableMoves( initialMoves );
            playerPrompt( currentPlayer );
            String input = Application.input.nextLine();
            if (input.equalsIgnoreCase( "exit" ) || input.equalsIgnoreCase( "quit" ))
                exitGame();
            if (board.tryPlayingPosition( currentPlayer, input, piece.createCheckerPiece( new Position(), currentPlayer.getColor(), false ) ))
                return;
        }
    }


    private boolean playerHasWon(Player currentPlayer) {
        return board.checkWin( currentPlayer );
    }


}
