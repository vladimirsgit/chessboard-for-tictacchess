package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.LinearPieceHelper;

public class Rook extends Piece{

    public Rook(String color, String symbol){
        super(color, symbol);
    }


    @Override
    public void validateMove(Position from, Position to, Game game) {
        if(isNotInsideChessboard(from, to)){
            throw new OutOfBoardBoundsException("The position/positions are out of the boards' bounds");
        }
        if(isVerticalOrHorizontalMove(from, to, game)){
            return;
        }
        throw new InvalidMoveException("Invalid move.");
    }

    private boolean isVerticalOrHorizontalMove(Position from, Position to, Game game){
        return LinearPieceHelper.makeVerticalOrHorizontalMoveOrCapture(from, to, game);
    }

//    private boolean isCapture(Position from, Position to, Game game){
//
//    }
}
