package utils;

import chess.Game;
import chess.Position;

public class LinearPieceTestUtils {
    private static final Position center = new Position(4, 4);

    public static void setUpRook(Game game){
        Position from = new Position(1, 1);
        game.movePieceAtCertainPosition(from, center);
    }
    public static void setUpBishop(Game game){
        Position from = new Position(8, 3);
        game.movePieceAtCertainPosition(from, center);
    }
    public static void setUpQueen(Game game){
        Position from = new Position(8, 4);
        game.movePieceAtCertainPosition(from, center);
    }
}
