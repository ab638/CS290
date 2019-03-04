package boardgame;

import gomoku.GomokuGameplay;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Application {
    public static Scanner input = new Scanner(System.in);


    List<TwoPlayerGame> games = new Vector<>();

    public static void main(String[] args) {
        Application application = new Application();
        application.run();

    }

    Application() {
        games = initGame();
    }

    public void run() {
        Vector<TwoPlayerGame> game = initGame();
        game.get(0).run();

    }

    private Vector<TwoPlayerGame> initGame() {
        Vector<TwoPlayerGame> game = new Vector<>();
        game.add(new GomokuGameplay(new Player("Player 1", Piece.Colors.BLACK),
                new Player("Player 2", Piece.Colors.WHITE)));
        return game;
    }
}
