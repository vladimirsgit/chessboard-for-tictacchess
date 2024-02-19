package utils;

import chess.Game;
import chess.Position;
import chess.pieces.Piece;

public class KnightTestHelper {
    private static final Position center = new Position(4, 4);
    private static final Position margin = new Position(4, 1);
    public static void setUpKnightForMoving(Game game){
        game.clearPawns();
        Position from = new Position(8 , 2);
        game.movePieceAtCertainPosition(from, center);
    }
    public static void setUpKnightForFailing(Game game){
        Position from = new Position(8, 2);
        game.movePieceAtCertainPosition(from, margin);
    }
}
