package boardgame;

import java.util.regex.Pattern;

public class AlgebraicNotationParser {
    protected String moveString;
    protected String positionRegex;

    public AlgebraicNotationParser(String regex, String moveString) {
        this.moveString = moveString;
        this.positionRegex = regex;
    }

    public boolean isValidMoveString(String moveString) {
        Pattern pattern = Pattern.compile( "^" + positionRegex + "-" + positionRegex + "$" );
        return pattern.matcher( moveString ).matches();
    }

    public boolean isValidPosition(String positionString) {
        Pattern pattern = Pattern.compile( "^" + positionRegex + "$" );
        return pattern.matcher( positionString ).matches();
    }

    public Position parsePosition(String positionString) {
        if (!isValidPosition( positionString ))
            throw new IllegalArgumentException( "String not a valid position string" );

        positionString = positionString.toLowerCase();
        int row = positionString.charAt( 1 ) - '0' - 1;
        int column = (int) positionString.toUpperCase().charAt( 0 ) - 'A';
        return new Position( row, column );
    }

}
