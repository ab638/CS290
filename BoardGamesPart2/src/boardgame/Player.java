package boardgame;


public class Player {
    private String name;
    private Colors color;


    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getColorShortName() {
        switch (color) {
            case BLACK:
                return "b";
            case WHITE:
                return "w";
            case EMPTY:
                return ".";
        }
        return null;

    }

}
