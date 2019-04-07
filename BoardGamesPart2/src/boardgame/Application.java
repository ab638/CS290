package boardgame;


import checkers.CheckersGameplay;
import chess.ChessGameplay;
import gomoku.GomokuGameplay;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Application {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static Scanner input = new Scanner( System.in );
    private List<TwoPlayerGame> games;

    private Application() {
        games = initGame();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.run();


    }

    private void run() {
        Vector<TwoPlayerGame> game = initGame();
        System.out.println( "Select a Game:" );
        System.out.println( ANSI_RED + "1. Gomoku" + ANSI_RESET );

        System.out.println( ANSI_BLUE + "2. Checkers" + ANSI_RESET );
        System.out.println( ANSI_WHITE + "3. Chess" + ANSI_RESET );
        System.out.println( "4. Exit" );
        int choice;
        boolean inputValid;
        do {
            System.out.print( "Enter Selection: " );

            choice = input.nextInt();
            if (choice < 5 && choice > 0)
                inputValid = true;
            else {
                System.out.println( "Choice Invalid. Try Again." );
                inputValid = false;
            }
        } while (!inputValid);
        input.nextLine();
        if (choice < 4) {
            game.get( choice - 1 ).run();
        } else {
            System.out.println( "Goodbye!" );
            System.exit( 0 );
        }
        input.close();
    }

    private Vector<TwoPlayerGame> initGame() {
        Vector<TwoPlayerGame> game = new Vector<>();
        game.add( new GomokuGameplay( new Player( "Player 1", Colors.BLACK ),
                new Player( "Player 2", Colors.WHITE ) ) );
        game.add( new CheckersGameplay( new Player( "Player 1", Colors.BLACK ),
                new Player( "Player 2", Colors.WHITE ) ) );
        game.add( new ChessGameplay( new Player( "Player 1", Colors.WHITE ),
                new Player( "Player 2", Colors.BLACK ) ) );
        return game;
    }
}
