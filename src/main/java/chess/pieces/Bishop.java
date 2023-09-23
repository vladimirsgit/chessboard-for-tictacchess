package chess.pieces;

import chess.Game;
import chess.Position;

public class Bishop extends Piece{

    public Bishop(String color, String symbol){
        super(color, symbol);
    }

    @Override
    public void validateMove(Position from, Position to, Game game) {
    }

}
