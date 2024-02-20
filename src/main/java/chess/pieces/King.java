package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.KingHelper;

public class King extends Piece{
    private boolean canCastle = true;
    private boolean inCheck = false;
    public King(String color, String symbol){
        super(color, symbol);
    }


    @Override
    public void validateMove(Position from, Position to, Game game) {
        if(from.isOutsideBoardBounds() || to.isOutsideBoardBounds()){
            throw new OutOfBoardBoundsException("The position/positions are out of the boards' bounds");
        }
        if(KingHelper.checkOneSquareMove(from, to) && !game.isSquareAttackedByOppColor(to, getColor())) {
            this.canCastle = false;
            return;
        }
        throw new InvalidMoveException("Invalid move.");
    }

    public boolean isInCheck() {
        return inCheck;
    }
}
