package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.BackwardsPawnMoveException;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.PawnHelper;

public class Pawn extends Piece{

    public Pawn(String color, String symbol){
        super(color, symbol);
    }


    @Override
    public void validateMove(Position from, Position to, Game game) {
        //if its not inside the chess board, NOT VALID
        if(from.isOutsideBoardBounds() || to.isOutsideBoardBounds()){
            throw new OutOfBoardBoundsException("The position/positions are out of the boards' bounds");
        }
        //if its trying to move backwards, return false
        if(PawnHelper.triesToMoveBackwards(from, to, game)){
            throw new BackwardsPawnMoveException("You cannot move the pawn backwards!");
        }
        if(isForwardMove(from, to, game) || isCapture(from, to, game) || isEnPassant(from, to, game)) return;

        throw new InvalidMoveException("Invalid move.");
    }
    /**
     * Checking if its a forward one square move.
     * @param from The initial position
     * @param to The wanted position
     * @param game The current state of the gameboard
     * @return Returns true if its a valid one square move or two squares move
     */
    private boolean isForwardMove(Position from, Position to, Game game){
        return PawnHelper.oneSquareForwardMove(from, to, game)
                || PawnHelper.twoSquaresForwardMove(from, to, game);
    }
    /**
     * Checking if its a valid capture.
     * @param from The initial position
     * @param to The wanted position
     * @param game The current state of the gameboard
     * @return returns true if the move is diagonal, the square is NOT empty and the color of the pieces are NOT the same
     */
    private boolean isCapture(Position from, Position to, Game game) {
        return PawnHelper.isDiagonalMove(from, to) && !game.isSquareEmpty(to)
                && Piece.isDifferentColorForCapturing(game.getPieceAtPosition(from), game.getPieceAtPosition(to));
    }

    private boolean isEnPassant(Position from, Position to, Game game){
        return PawnHelper.tryForEnPassant(from, to, game);
    }
    public void tryme(){

    }
}
