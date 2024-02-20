package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.KingTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
    private Game game;

    @BeforeEach
    public void setUpGame(){
        this.game = new Game();
    }
    @ParameterizedTest
    @CsvSource({
            "4, 4, 3, 3", // Down and to the left
            "4, 4, 3, 4", // Left
            "4, 4, 3, 5", // Up and to the left
            "4, 4, 4, 3", // Down
            "4, 4, 4, 5", // Up
            "4, 4, 5, 3", // Down and to the right
            "4, 4, 5, 4", // Right
            "4, 4, 5, 5" // Up and to the right
    })
    public void testValidMoves(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        KingTestUtils.setUpKingForNormalMoves(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece king = game.getPieceAtPosition(from);

        king.move(from, to, game);

        assertEquals(game.getPieceAtPosition(to), king);
        assertNull(game.getPieceAtPosition(from));
    }
    @ParameterizedTest
    @CsvSource({
            "5, 4, 3, 4", // Down 2 squares
            "5, 4, 3, 6", // 2 squares diag
            "5, 4, 3, 5", // L shaped move
            "5, 4, 5, 6", // next to opp color king
    })
    public void testInvalidMoves(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        KingTestUtils.setUpKingForInvalidMoves(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece king = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
           king.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), king);
        game.printGameBoard();
    }
    @ParameterizedTest
    @CsvSource({
            "4, 4, 5, 5", // try to move in check
            "4, 4, 5, 4", // try to capture protected piece
    })
    public void testMovingInCheck(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        KingTestUtils.setUpKingForCheckTest(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece king = game.getPieceAtPosition(from);
        game.setAttackedSquares();
        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
            king.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), king);
        game.printGameBoard();
    }
    // TODO test castling
}
