package gomoku;

import boardgame.Application;
import boardgame.Player;
import boardgame.Position;
import boardgame.TwoPlayerGame;

public class GomokuGameplay extends TwoPlayerGame {
    private GomokuBoard board; //  a game has a board and players


    public GomokuGameplay(Player player1, Player player2) {
        super( player1, player2 );
        board = new GomokuBoard();
    }

    private void menu() {
        System.out.println( "\t\t\t\t\t ----------------------" );
        System.out.println( "\t\t\t\t\t|       Gomanku        |" );
        System.out.println( "\t\t\t\t\t ----------------------" );
        System.out.println( "Rules:" );
        System.out.println( "1. to place a piece use the following format: Column-Row" );
        System.out.println( "2. Get 5 in a row" );
        System.out.println( "3. Type 'quit' or 'exit' to exit the game.\n" );

    }

    @Override
    public void run() {
        menu();
        board.init();
        int moves = 1;
        board.setMoves( moves );
        while (true) {
            board.print();
            playerTurn( currentPlayer );
            if (board.isDraw()) {
                board.print();
                System.out.println( "It is a draw! Thanks for playing!" );
                System.exit( 0 );
            }
            if (currentPlayerHasWon()) {
                playerWins( currentPlayer );
            }
            nextPlayer();
            moves++;
            board.setMoves( moves );
        }

    }

    private void playerTurn(Player currentPlayer) {

        GomokuPiece piece = new GomokuPiece();
        while (true) {
            playerPrompt( currentPlayer );
            String input = Application.input.nextLine();
            if (input.equalsIgnoreCase( "exit" ) || input.equalsIgnoreCase( "quit" ))
                exitGame();

            if (!board.matchedPattern( input )) {
                System.out.println( "Bad format." );
            }

            int column = (int) input.toUpperCase().charAt( 0 ) - 65; // gets back to 0
            int row;
            try {
                if (input.length() == 3) {
                    char tmp = input.charAt( 1 );
                    char tmp2 = input.charAt( 2 );
                    String tmpStr = tmp + "" + tmp2;
                    row = Integer.parseInt( tmpStr );
                } else
                    row = input.charAt( 1 ) - '0';
                Position position = new Position( row - 1, column );
                if (board.tryPlayingPosition( piece.createPiece( position, currentPlayer.getColor() ) ))
                    return;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println( "Formatting incorrect. Try again." );
            }
            board.print();
        }
    }

    private void playerPrompt(Player player) {
        System.out.println( "Choose a position, " + player.getName() + "." );
        if (player.getColor().toString().equalsIgnoreCase( "black" ))
            System.out.println( "Your piece is " + player.getColor().toString() +
                    " The ANSI Color looks like this: " + Application.ANSI_BLACK + player.getColorShortName() + Application.ANSI_RESET );
        if (player.getColor().toString().equalsIgnoreCase( "white" ))
            System.out.println( "Your piece is " + player.getColor().toString()
                    + " The ANSI Color looks like this: " + Application.ANSI_WHITE + player.getColorShortName() + Application.ANSI_RESET );
        System.out.print( "Enter location: " );
    }


    private void playerWins(Player player) {

        System.out.println( "\nCongratulations, " + player.getName() + "! You won!\n" );
        board.print();
        System.exit( 0 );
    }

    private boolean currentPlayerHasWon() {
        return board.checkWin( currentPlayer );
    }


}
