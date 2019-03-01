package gomoku;

import boardgame.Piece;

public class GomokuPiece extends Piece {
    static GomokuPiece createPiece(Colors color) {
        if (color == Colors.BLACK) {
            return new BlackGomokuPiece();
        } else if (color == Colors.WHITE) {
            return new WhiteGomokuPiece();
        }
        throw new IllegalArgumentException("Invalid Gomoku Piece Color");
    }
}
