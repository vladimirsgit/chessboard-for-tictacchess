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

public class BishopTets {
    private Game game;

    @BeforeEach
    public void setUpGame(){
        this.game = new Game();
    }
    @ParameterizedTest
    @CsvSource({
            "4, 4, 3, 5", //from (4, 4) to (3, 5) - to the left
            "4, 4, 3, 3", //from (4, 4) to (3, 3) - to the right
            "4, 4, 6, 6", //from (4, 4) to (6, 6) - left backwards
            "4, 4, 6, 2",  //from (4, 4) to (6, 2) - right backwards
            "4, 4, 2, 6",  //from (4, 4) to (7, 4) - try to capture white pawn
    })
    public void testValidMovements(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        LinearPieceTestUtils.setUpBishop(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece bishop = game.getPieceAtPosition(from);

        bishop.move(from, to, game);
        assertEquals(game.getPieceAtPosition(to), bishop);
        game.printGameBoard();
    }
    @ParameterizedTest
    @CsvSource({
            "4, 4, 4, 1", //from (4, 4) to (4, 1) - to the left
            "4, 4, 4, 8", //from (4, 4) to (4, 8) - to the right
            "4, 4, 6, 4", //from (4, 4) to (6, 4) - up
            "4, 4, 3, 4",  //from (4, 4) to (3, 4) - down
            "4, 4, 7, 7", //from (4, 4) to (7, 7) - try to capture same colored piece
    })
    public void testInvalidMovements(int fromVerticalCoord, int fromHorizontalCoord, int toVerticalCoord, int toHorizontalCoord){
        LinearPieceTestUtils.setUpBishop(game);
        Position from = new Position(fromVerticalCoord, fromHorizontalCoord);
        Position to = new Position(toVerticalCoord, toHorizontalCoord);
        Piece bishop = game.getPieceAtPosition(from);

        InvalidMoveException thrownException = assertThrows(InvalidMoveException.class, () -> {
           bishop.validateMove(from, to, game);
        });
        assertEquals(game.getPieceAtPosition(from), bishop);
    }
}
