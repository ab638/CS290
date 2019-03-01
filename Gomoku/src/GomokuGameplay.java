public class GomokuGameplay extends TwoPlayerGame {
    private GomokuBoard board; //  a game has a board and players


    public GomokuGameplay(Player player1, Player player2, GomokuBoard board) {
        super(player1, player2);
        this.board = board;
    }

    private void menu() {
        System.out.println("\t\t\t\t\t ----------------------");
        System.out.println("\t\t\t\t\t|       Gomanku        |");
        System.out.println("\t\t\t\t\t ----------------------");
        System.out.println("Rules:");
        System.out.println("1. to place a piece use the following format: Column-Row");
        System.out.println("2. Get 5 in a row");
    }

    @Override
    public void run() {
        menu();
        board.init();
        int moves = 1;
        board.setMoves(moves);
        while (true) {
            board.print();
            playerTurn(currentPlayer);
            if (board.isDraw()) {
                board.print();
                System.out.println("It is a draw! Thanks for playing!");
                System.exit(0);
            }
            if (currentPlayerHasWon()) {
                playerWins(currentPlayer);
            }
            nextPlayer();
            moves++;
            board.setMoves(moves);
        }

    }

    private void playerTurn(Player currentPlayer) {

        while (true) {
            playerPrompt(currentPlayer);
            String input = Application.input.nextLine();
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit"))
                exitGame();
            if (board.tryPlayingPosition(input, currentPlayer))
                return;
            board.print();
        }
    }

    private void playerPrompt(Player player) {
        System.out.println("Choose a position, " + player.getName() + ".");
        System.out.println("Your piece is " + player.getColor().toString());
        System.out.print("Enter location: ");
    }


    private void exitGame() {
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    private void playerWins(Player player) {

        System.out.println("\nCongratulations, " + player.getName() + "! You won!\n");
        board.print();
        System.exit(0);
    }

    private boolean currentPlayerHasWon() {
        return board.doesPlayerWin(currentPlayer);
    }


}
