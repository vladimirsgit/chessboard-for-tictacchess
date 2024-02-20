package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.KnightTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KnightTest {
    private Game game;

    @BeforeEach
    public void setUpGame(){
        this.game = new Game();
    }

    @ParameterizedTest
    @CsvSource({
            "4, 4, 6, 5", // from (4, 4) to (6, 5) - up and to the right
            "4, 4, 6, 3", // from (4, 4) to (6, 3) - down and to the right
            "4, 4, 2, 5", // from (4, 4) to (2, 5) - up and to the left
            "4, 4, 2, 3", // from (4, 4) to (2, 3) - down and to the left
            "4, 4, 5, 6", // from (4, 4) to (5, 6) - right and up
            "4, 4, 5, 2", // from (4, 4) to (5, 2) - right and down
            "4, 4, 3, 6", // from (4, 4) to (3, 6) - left and up
            "4, 4, 3, 2", // from (4, 4) to (3, 2) - left and down
    })
    public void testValidMoves(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        KnightTestUtils.setUpKnightForMoving(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece knight = game.getPieceAtPosition(from);

        knight.move(from, to, game);

        assertEquals(game.getPieceAtPosition(to), knight);
    }
    @Test
    public void testOutOfBoardBoundsMove(){
        KnightTestUtils.setUpKnightForFailing(game);

        Position from = new Position(4, 1);
        Position to = new Position(4, 0);
        Piece knight = game.getPieceAtPosition(from);

        OutOfBoardBoundsException thrownException = assertThrows(OutOfBoardBoundsException.class, () -> {
            knight.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), knight);
    }

    @Test
    public void testInvalidCapture(){
        Position from = new Position(8, 2);
        Position to = new Position(7, 4);
        Piece knight = game.getPieceAtPosition(from);
        Piece piece = game.getPieceAtPosition(to);
        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
            knight.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), knight);
        assertEquals(game.getPieceAtPosition(to), piece);
    }
}
