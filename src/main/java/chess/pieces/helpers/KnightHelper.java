package chess.pieces.helpers;

import chess.Game;
import chess.Position;
import chess.exceptions.InvalidMoveException;
import chess.pieces.Piece;

import java.util.Objects;

public class KnightHelper {
    public static boolean makeMove(Position from, Position to, Game game){
        if(!checkLMove(from, to)) return false;
        Piece pieceToBeCaptured = game.getPieceAtPosition(to);
        Piece pieceToBeMoved = game.getPieceAtPosition(from);
        if(pieceToBeCaptured == null) return true;
        if(Objects.equals(pieceToBeCaptured.getColor(), pieceToBeMoved.getColor())) return false;

        return true;
    }


    private static boolean checkLMove(Position from, Position to){
        int fromVertical = from.getVerticalCoord();
        int fromHorizontal = from.getHorizontalCoord();
        int toVertical = to.getVerticalCoord();
        int toHorizontal = to.getHorizontalCoord();

        boolean isVertical = Math.abs(fromVertical - toVertical) == 2 && Math.abs(fromHorizontal - toHorizontal) == 1;
        boolean isHorizontal = Math.abs(fromHorizontal - toHorizontal) == 2 && Math.abs(fromHorizontal - toVertical) == 1;

        return isVertical || isHorizontal;
    }

}
