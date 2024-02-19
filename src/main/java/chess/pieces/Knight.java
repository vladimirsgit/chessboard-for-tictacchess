package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.KnightHelper;

public class Knight extends Piece{

    public Knight(String color, String symbol){
        super(color, symbol);
    }

    @Override
    public void validateMove(Position from, Position to, Game game) {
        if(from.isOutsideBoardBounds() || to.isOutsideBoardBounds()){
            throw new OutOfBoardBoundsException("The position/positions are out of the boards' bounds");
        }
        if(KnightHelper.makeMove(from, to, game)) return;
        throw new InvalidMoveException("Invalid move");

    }
}
