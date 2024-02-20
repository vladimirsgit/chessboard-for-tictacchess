package chess.pieces.helpers;

import chess.Game;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;

import java.util.HashMap;
import java.util.HashSet;


public class KingHelper {

    public static boolean checkOneSquareMove(Position from, Position to){
        int fromVerticalCoord = from.getVerticalCoord();
        int fromHorizontalCoord = from.getHorizontalCoord();
        int toVerticalCoord = to.getVerticalCoord();
        int toHorizontalCoord = to.getHorizontalCoord();

        return Math.abs(fromVerticalCoord - toVerticalCoord) <= 1 && Math.abs(fromHorizontalCoord - toHorizontalCoord) <= 1;
    }
    public static boolean isCastleMove(boolean canCastle, Position from, Position to, Game game, King king){
        if(king.isInCheck()) return false;
        if(!(moveIsHorizontal(from, to) && canCastle)) return false;
        if(!isFreePath(from.getVerticalCoord(), from.getHorizontalCoord(), to.getHorizontalCoord(), game, king.getColor())) return false;
        return canRookCastle(from.getHorizontalCoord(), to.getHorizontalCoord(), game, king.getColor());
    }
    public static boolean moveIsHorizontal(Position from, Position to){
        int fromHorizontal = from.getHorizontalCoord();
        int toHorizontal = to.getHorizontalCoord();
        return from.getVerticalCoord() == to.getVerticalCoord() && Math.abs(fromHorizontal - toHorizontal) == 2;
    }
    public static boolean canRookCastle(int fromHorizontalCoord, int toHorizontalCoord, Game game, String kingColor){
        if(fromHorizontalCoord < toHorizontalCoord) {
            return checkKingSideRook(kingColor, game);
        } else return checkQueenSideRook(kingColor, game);
    }
    public static boolean checkQueenSideRook(String kingColor, Game game){
        Position position;
        if(kingColor.equals(Piece.WHITE)){
            position = new Position(1, 1);
        } else {
            position = new Position(8, 1);
        }
        Piece rook = game.getPieceAtPosition(position);
        if(rook instanceof Rook){
            return ((Rook) rook).canCastle() && rook.getColor().equals(kingColor);
        }
        return false;
    }
    public static boolean checkKingSideRook(String kingColor, Game game){
        Position position;
        if(kingColor.equals(Piece.WHITE)){
            position = new Position(1, 8);
        } else {
            position = new Position(8, 8);
        }
        Piece rook = game.getPieceAtPosition(position);
        if(rook instanceof Rook){
            return ((Rook) rook).canCastle() && rook.getColor().equals(kingColor);
        }
        return false;
    }
    public static boolean isFreePath(int verticalCoord, int fromHorizontalCoord, int toHorizontalCoord, Game game, String kingColor){
        int increment = fromHorizontalCoord  < toHorizontalCoord ? 1 : -1;
        for(int currCoord = fromHorizontalCoord; currCoord != Math.abs(toHorizontalCoord - 1); currCoord+=increment){
            Position position = new Position(verticalCoord, currCoord);
            if(!game.isSquareEmpty(position)) return false;
            if(game.isSquareAttackedByOppColor(position, kingColor)) return false;
        }
        return true;
    }
    public static void setKingAttackedSquares(HashMap<Position, HashSet<String>> attackedSquares, Position kingPosition, Piece king){
        int verticalCoord = kingPosition.getVerticalCoord();
        int horizontalCoord = kingPosition.getHorizontalCoord();
        int[][] positions = {
                {verticalCoord + 1, horizontalCoord - 1}, {verticalCoord + 1, horizontalCoord}, {verticalCoord + 1, horizontalCoord + 1},
                {verticalCoord, horizontalCoord + 1}, {verticalCoord - 1, horizontalCoord + 1}, {verticalCoord - 1, horizontalCoord},
                {verticalCoord - 1, horizontalCoord - 1}, {verticalCoord, horizontalCoord - 1}
        };
        for (int[] posArr : positions){
            Position position = new Position(posArr[0], posArr[1]);
            if(!position.isOutsideBoardBounds()){
                addSquare(position, attackedSquares, king);
            }
        }
    }
    private static void addSquare(Position pos, HashMap<Position, HashSet<String>> attackedSquares, Piece king){
        if(attackedSquares.containsKey(pos)){
            attackedSquares.get(pos).add(king.getColor());
        } else {
            HashSet<String> colors = new HashSet<>();
            colors.add(king.getColor());
            attackedSquares.put(pos, colors);
        }
    }
}
