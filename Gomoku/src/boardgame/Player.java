package boardgame;


public class Player {
    private String name;
    private Piece.Colors color;

    public Player() {
        this("", Piece.Colors.EMPTY);
    }

    public Player(String name, Piece.Colors color) {
        this.name = name;
        this.color = color;
    }

    public Piece.Colors getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getColorShortName() {
        switch (color) {
            case BLACK:
                return "B";
            case WHITE:
                return "W";
            case EMPTY:
                return ".";
        }
        return null;

    }
}
