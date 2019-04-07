package boardgame;

public abstract class TwoPlayerGame {

    protected Player currentPlayer;
    protected Player otherPlayer;

    public TwoPlayerGame(Player player1, Player player2) {

        currentPlayer = player1;
        otherPlayer = player2;
    }

    protected void nextPlayer() {
        Player tmp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tmp;
    }

    protected abstract void menu();

    public abstract void run();

    public void exitGame() {
        System.out.println( "Thanks for playing!" );
        System.exit( 0 );
    }

}
