package chess.pieces;

import chess.Game;
import chess.Position;
import chess.exceptions.BackwardsPawnMoveException;
import chess.exceptions.InvalidMoveException;
import chess.exceptions.OutOfBoardBoundsException;
import chess.pieces.helpers.PawnHelper;

import java.util.Objects;

public abstract class Piece {
    private final String symbol;
    private final String color;

    public static final String WHITE = "white";
    public static final String BLACK = "black";

    public Piece(String color, String symbol){
        this.color = color;
        this.symbol = symbol;
    }
    public  String getColor() {
        return this.color;
    }

    public String getSymbol(){
        return this.symbol;
    }
    public abstract void validateMove(Position from, Position to, Game game);
    public void move(Position from, Position to, Game game){

        try{
            validateMove(from, to, game);
            game.setEnPassantCandidate(null);
        } catch (InvalidMoveException | OutOfBoardBoundsException | BackwardsPawnMoveException e){
            System.out.println(e.getMessage());
            return;
        }
        Piece[][] gameBoard = game.getGameBoard();
        Piece currPositionPiece = gameBoard[from.getVerticalCoord()][from.getHorizontalCoord()];

        if(PawnHelper.twoSquaresForwardMove(from, to, game)){
            game.setEnPassantCandidate(currPositionPiece);
        }
        gameBoard[to.getVerticalCoord()][to.getHorizontalCoord()] = currPositionPiece;
        gameBoard[from.getVerticalCoord()][from.getHorizontalCoord()] = null;
    }

    public boolean isNotInsideChessboard(Position from, Position to){
        return from.getVerticalCoord() < 1|| from.getVerticalCoord() > 8 || from.getHorizontalCoord() < 1 || from.getHorizontalCoord() > 8
                || to.getVerticalCoord() < 1 || to.getVerticalCoord() > 8 || to.getHorizontalCoord() < 1 || to.getHorizontalCoord() > 8;
    }

    public static boolean isDifferentColorForCapturing(Piece piece1, Piece piece2){
        return !Objects.equals(piece1.getColor(), piece2.getColor());
    }

}
