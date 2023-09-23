package utils;

import chess.Game;
import chess.Position;
import chess.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;

public class RookTestUtils {

    public static void setUpForRookTests(Game game){
        Position from = new Position(1, 1);
        Position to = new Position(4, 4);
        Piece rook = game.getPieceAtPosition(from);

        game.movePieceAtCertainPosition(from, to);
    }
}
