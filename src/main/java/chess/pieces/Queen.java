package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.LinearPieceHelper;

public class Queen extends Piece{

    public Queen(String color, String symbol){
        super(color, symbol);
    }


    @Override
    public void validateMove(Position from, Position to, Game game) {
        if(isNotInsideChessboard(from, to)) throw new OutOfBoardBoundsException("Outside of boards' bounds");
        if(LinearPieceHelper.makeDiagonalMoveOrCapture(from, to, game) || LinearPieceHelper.makeVerticalOrHorizontalMoveOrCapture(from, to, game)) return;
        throw new InvalidMoveException("Invalid move.");
    }
}
