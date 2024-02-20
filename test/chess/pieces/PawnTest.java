package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.BackwardsPawnMoveException;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PawnTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    private Game game;


    @BeforeEach
    public void setUpGame(){
        this.game = new Game();
    }

    @ParameterizedTest
    @CsvSource({
            "7, 1, 6, 1", //from (7, 1) to (6, 1) - one square black
            "7, 1, 5, 1",  // from (7, 1) to (5, 1) - two squares black

            "2, 1, 3, 1", //from (2, 1) to (3, 1) - one square white
            "2, 1, 4, 1"  // from (2, 1) to (4, 1) - two squares white
    })
    public void testValidMovesFromStartPosition(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        //the left most white pawn
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        pawn.move(from, to, game);
        game.printGameBoard();
        assertNull(game.getPieceAtPosition(from));
        assertEquals(game.getPieceAtPosition(to), pawn);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1, 5, 1", //from (2, 1) to (5, 1) - three squares black
            "2, 1, 3, 2", // from (2, 1) to (3, 2) - diag with no capture black
            "2, 1, 5, 3", // from (2, 1) to (5, 3) - 3 up, 2 right black
            "2, 1, 2, 2", // from (2, 1) to (2, 2) - on top of the same colored right pawn black

            "7, 1, 4, 1", //from (2, 1) to (5, 1) - three squares white
            "7, 1, 6, 2", // from (2, 1) to (2, 1) - diag with no capture white
            "7, 1, 4, 3", // from (2, 1) to (5, 2) - 3 up, 2 left white
            "7, 1, 7, 2"  // from (2, 1) to (2, 2) - on top of the same colored right pawn white
    })
    public void testTryingInvalidMovesFromStartPosition(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
            pawn.validateMove(from, to, game);
        });
    }

    @Test
    public void testOutOfBoardBounds(){
        Position from = new Position(2, 1);
        Position to = new Position(-1, 1);
        Piece pawn = game.getPieceAtPosition(from);

        OutOfBoardBoundsException thrownException = assertThrows(OutOfBoardBoundsException.class, () -> {
            pawn.validateMove(from, to, game);
        });
    }
    @ParameterizedTest
    @CsvSource({
            "2, 1, 1, 1", // from (2, 1) to (1, 1) - backward black
            "7, 1, 8, 1"  // from (7, 1) to (8 ,1) - backward white
    })
    public void testBackwardMovementFromStartPosition(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        BackwardsPawnMoveException thrownException = assertThrows(BackwardsPawnMoveException.class, () -> {
            pawn.validateMove(from, to, game);
        });
    }

    @ParameterizedTest
    @CsvSource({
        "6, 4, 7, 3", // from (6, 4) to (7, 3) - capture to the left
        "6, 4, 7, 5"  // from (6, 4) to (7 ,5) - capture to the right
    })
    public void testValidCaptures(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        PawnTestUtils.setUpPawnsForBlockingPathAndCapturingTestsAndBackwardAndLateralMovementTests(game);

        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        pawn.move(from, to, game);

        assertNull(game.getPieceAtPosition(from));
        assertEquals(game.getPieceAtPosition(to), pawn);
    }
    @ParameterizedTest
    @CsvSource({
            "6, 4, 7, 4", // from (6, 4) to (7, 4) - move forward with blocked path
            "6, 4, 8, 4", // from (6, 4) to (8 ,4) - move 2 squares forward with blocked path
            "6, 4, 8, 2", // from (6, 4) to (8, 2) - try to capture diagonally 2 squares left
            "6, 4, 8, 6" // from (6, 4) to (8, 6) - try to capture diagonally 2 squares right
    })
    public void testInvalidMoveAndInvalidCapture(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        PawnTestUtils.setUpPawnsForBlockingPathAndCapturingTestsAndBackwardAndLateralMovementTests(game);

        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
            pawn.validateMove(from, to, game);
        });
    }
    @ParameterizedTest
    @CsvSource({
            "6, 4, 5, 4", // from (6, 4) to (5, 4) - backward one free square
            "6, 4, 4, 4", // from (6, 4) to (4, 4) - backward 2 free squares
            "6, 4, 4, 3", // from (6, 4) to (4, 3) - backward horse like move L shaped
            "6, 4, 4, 2"  // from (6, 4) to (4, 2) - backward diag two squares
    })
    public void testBackwardMovementWithFreeSpaceBehind(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        PawnTestUtils.setUpPawnsForBlockingPathAndCapturingTestsAndBackwardAndLateralMovementTests(game);

        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        BackwardsPawnMoveException thrownException = assertThrows(BackwardsPawnMoveException.class, () -> {
            pawn.validateMove(from, to, game);
        });
    }
    @ParameterizedTest
    @CsvSource({
            "6, 4, 6, 3", // from (6, 4) to (6, 3) - left one square
            "6, 4, 6, 2", // from (6, 4) to (6, 2) - left two squares
            "6, 4, 6, 5", // from (6, 4) to (6, 5) - right one square
            "6, 4, 6, 6"  // from (6, 4) to (6, 6) - right two squares
    })
    public void testLateralMovement(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        PawnTestUtils.setUpPawnsForBlockingPathAndCapturingTestsAndBackwardAndLateralMovementTests(game);

        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece pawn = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
            pawn.validateMove(from, to, game);
        });
    }
    @ParameterizedTest
    @CsvSource({
            "true", //piece is en passantable
            "false" // piece is not en passantable anymore
    })
    public void testEnPassant(boolean valid){
        PawnTestUtils.setUpEnPassant(game, valid);

        Position from = new Position(5, 4);
        Position to = new Position(6, 5);
        Piece pawn = game.getPieceAtPosition(from);

        if(!valid){
            InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
                pawn.validateMove(from, to, game);
            });
            return;
        }

        pawn.move(from, to, game);
        //making sure the piece has moved
        assertNull(game.getPieceAtPosition(from));
        //making sure the en passantable pawn was taken and now the square is empty
        assertNull(game.getPieceAtPosition(new Position(5, 5)));
        //making sure the piece actually arrived where it was supposed to
        assertEquals(game.getPieceAtPosition(to), pawn);
    }
}
