package chess.pieces.helpers;

import chess.Game;
import chess.Position;
import chess.pieces.Piece;

import java.util.HashSet;
import java.util.Objects;

public class KnightHelper {
    public static boolean makeMove(Position from, Position to, Game game){
        if(!checkLMove(from, to)) return false;
        Piece pieceToBeCaptured = game.getPieceAtPosition(to);
        Piece pieceToBeMoved = game.getPieceAtPosition(from);
        if(pieceToBeCaptured == null) return true;
        return !Objects.equals(pieceToBeCaptured.getColor(), pieceToBeMoved.getColor());
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
    public static void setKnightAttackedSquares(HashSet<Position> attackedSquares, Position piecePosition, Piece[][] gameboard){
        int i = piecePosition.getVerticalCoord();
        int j = piecePosition.getHorizontalCoord();
        int[][] knightMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for(int[] position : knightMoves){
            Position attackedPos = new Position(i + position[0], j + position[1]);
            if(!attackedPos.isOutsideBoardBounds()){
                attackedSquares.add(attackedPos);
            }
        }
    }

}
