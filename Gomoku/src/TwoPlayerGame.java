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

    public abstract void run();
}
