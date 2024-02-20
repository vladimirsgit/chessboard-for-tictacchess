package utils;

import chess.Game;
import chess.Position;

public class KingTestUtils {
    public static void setUpKingForNormalMoves(Game game){
        game.movePieceAtCertainPosition(new Position(1, 5), new Position(4, 4));
    }
    public static void setUpKingForInvalidMoves(Game game){
        // move black king
        game.movePieceAtCertainPosition(new Position(8, 5), new Position(5, 6));
        // move white king
        game.movePieceAtCertainPosition(new Position(1, 5), new Position(5, 4));
    }
    public static void setUpKingForCheckTest(Game game){
        game.movePieceAtCertainPosition(new Position(1, 5), new Position(4, 4));
        // place black pawns where necessary
        game.movePieceAtCertainPosition(new Position(7, 6), new Position(6, 6));
        game.movePieceAtCertainPosition(new Position(7, 4), new Position(5, 4));
    }
}
