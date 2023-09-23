package utils;

import chess.Game;
import chess.Position;

public class PawnTestUtils {

    //takes the left middle white pawn and moves it up the middle
    public static void setUpPawnsForBlockingPathAndCapturingTestsAndBackwardAndLateralMovementTests(Game game){
        Position from = new Position(2, 4);
        Position to = new Position(6, 4);

        game.movePieceAtCertainPosition(from, to);
    }

    public static void setUpEnPassant(Game game, boolean valid){
        //move white pawn on (5, 4)
        Position from = new Position(2, 4);
        Position to = new Position(5 , 4);
        game.movePieceAtCertainPosition(from, to);

        //place black pawn in enPassantPositon
        from = new Position(7, 5);
        to = new Position(5, 5);
        game.movePieceAtCertainPosition(from, to);

        if(valid){
            game.setEnPassantCandidate(game.getPieceAtPosition(to));
        }
    }
}
