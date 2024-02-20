package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.LinearPieceTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RookTest {
    private Game game;

    @BeforeEach
    public void setUpGame(){
        this.game = new Game();
    }

    @ParameterizedTest
    @CsvSource({
            "4, 4, 4, 1", //from (4, 4) to (4, 1) - to the left
            "4, 4, 4, 8", //from (4, 4) to (4, 8) - to the right
            "4, 4, 6, 4", //from (4, 4) to (6, 4) - up
            "4, 4, 3, 4",  //from (4, 4) to (3, 4) - down
            "4, 4, 7, 4",  //from (4, 4) to (7, 4) - try to capture black pawn
    })
    public void testValidMovements(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        LinearPieceTestUtils.setUpRook(game);

        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece rook = game.getPieceAtPosition(from);

        rook.move(from, to, game);

        assertEquals(game.getPieceAtPosition(to), rook);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 4, 2, 4", //from (4, 4) to (2, 4) - capture same colored pawn
            "4, 4, 8, 4", //from (4, 4) to (8, 4) - capture piece with obstructed path
    })
    public void testInvalidCaptures(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        LinearPieceTestUtils.setUpRook(game);

        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece rook = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
           rook.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), rook);
    }
    @ParameterizedTest
    @CsvSource({
            "4, 4, 3, 5", //from (4, 4) to (3, 5) - to the left
            "4, 4, 3, 3", //from (4, 4) to (3, 3) - to the right
            "4, 4, 6, 6", //from (4, 4) to (6, 6) - left backwards
            "4, 4, 6, 2",  //from (4, 4) to (6, 2) - right backwards
    })
    public void testDiagonalMovement(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        LinearPieceTestUtils.setUpRook(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(fromVerticalCoord, fromHorizontalCoord);
        Piece rook = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
            rook.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), rook);
    }

}
