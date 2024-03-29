package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.LinearPieceHelper;

public class Bishop extends Piece{

    public Bishop(String color, String symbol){
        super(color, symbol);
    }

    @Override
    public void validateMove(Position from, Position to, Game game) {
        if(from.isOutsideBoardBounds() || to.isOutsideBoardBounds()){
            throw new OutOfBoardBoundsException("The position/positions are out of the boards' bounds");
        }
        if(LinearPieceHelper.makeDiagonalMoveOrCapture(from, to, game)) return;
        throw new InvalidMoveException("Invalid move.");
    }


}
