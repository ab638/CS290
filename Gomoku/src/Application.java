import java.util.Scanner;

public class Application {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        run();

    }

    public static void run() {
        Player p1 = new Player("Player 1", Colors.BLACK);
        Player p2 = new Player("Player 2", Colors.WHITE);

        GomokuGameplay gameplay;
        gameplay = new GomokuGameplay(p1, p2, new GomokuBoard());
        gameplay.run();

    }
}
