package chess.pieces.helpers;

import chess.Game;
import chess.Position;
import chess.pieces.Piece;

public class LinearPieceHelper {
    public static final String DIAGONAL = "DIAGONAL";
    public static final String INVALID = "INVALID";
    public static final String VERTICAL = "VERTICAL";
    public static final String HORIZONTAL = "HORIZONTAL";

    /**
     * Helper method used for vertical and horizontal moves. Useful for rooks and queens.
     * First it checks the direction of the movement. Then it sets up the startPos and endPos, meaning the square that will get changed.
     * If it goes vertical, obviously the vertical coord will get changed, and the same for horizontal movements.
     *
     * @param from The initial position.
     * @param to The wanted position.
     * @param game Current game state.
     * @return It returns the response of the {@link LinearPieceHelper#checkFreePathForVerticalAndHorizontalMove(int, int, String, Position, Position, Piece, Piece, Game)} method.
     */
    public static boolean makeVerticalOrHorizontalMoveOrCapture(Position from, Position to, Game game) {
        String direction = checkDirection(from, to);
        if(direction.equals(INVALID) || direction.equals(DIAGONAL)) {
            return false;
        }
        int startPos = 0, endPos = 0;
        startPos = from.getVerticalCoord();
        endPos = to.getVerticalCoord();

        if(direction.equals(HORIZONTAL)) {
            startPos = from.getHorizontalCoord();
            endPos = to.getHorizontalCoord();
        }
        Piece pieceToBeMoved = game.getPieceAtPosition(from);
        Piece pieceToBeCaptured = game.getPieceAtPosition(to);

        return checkFreePathForVerticalAndHorizontalMove(startPos, endPos, direction, to, pieceToBeMoved, pieceToBeCaptured, game);
    }
    /**
     * Checking if the path is obstructed.
     * If it reaches the wanted position and theres an opposite colored piece there, means its also a valid capture.
     * int increment value: this is helpful to save us from writing two for loops. we make sure that we always start from the position of the pieceToBeMoved, and go towards the wanted square;
     * @param startPos The start position of the loop, used together with endPos to see if the path is clear.
     * @param endPos The end position of the loop, used together with startPos to see if the path is clear.
     * @param direction Information if its going to be a vertical or horizontal path.
     * @param from The initial square.
     * @param to The wanted square.
     * @param pieceToBeMoved The piece wanting to be moved.
     * @param pieceToBeCaptured The possible capturable piece (can be null if the square is empty)
     * @param game Current game state.
     * @return It returns true if it reaches the end square and it can be captured, or if it reaches the end square and it was not obstructed by other pieces.
     */

    private static boolean checkFreePathForVerticalAndHorizontalMove(int startPos, int endPos, String direction, Position to, Piece pieceToBeMoved, Piece pieceToBeCaptured, Game game){
        int incrementValue = 1;
        if(startPos > endPos){
            incrementValue = -1;
        }
        for(int i = startPos; i != endPos + incrementValue; i+=incrementValue){
            Position currPos;
            if(direction.equals(VERTICAL)){
                currPos = new Position(i, to.getHorizontalCoord());
            } else {
                currPos = new Position(to.getVerticalCoord(), i);
            }
            if(pieceToBeCaptured != null && currPos.isSamePosition(to) && okToCapture(currPos, pieceToBeMoved, pieceToBeCaptured, game)) return true;
            if(!game.isSquareEmpty(currPos) && game.getPieceAtPosition(currPos) != pieceToBeMoved) return false;
        }
        return true;
    }

    public static boolean makeDiagonalMoveOrCapture(Position from, Position to, Game game){
        String direction = checkDirection(from, to);

        if(!direction.equals(DIAGONAL)){
            return false;
        }

        Piece pieceToBeMoved = game.getPieceAtPosition(from);
        Piece pieceToBeCaptured = game.getPieceAtPosition(to);

        return checkFreePathForDiagonalMove(from, to, pieceToBeMoved, pieceToBeCaptured, game);
    }
    private static boolean checkFreePathForDiagonalMove(Position from, Position to, Piece pieceToBeMoved, Piece pieceToBeCaptured, Game game){
        int incrementVertical = -1, incrementHorizontal = -1;
        if(from.getVerticalCoord() < to.getVerticalCoord()){
            incrementVertical = 1;
        }
        if(from.getHorizontalCoord() < to.getHorizontalCoord()){
            incrementHorizontal = 1;
        }

        for(int verticalCoord = from.getVerticalCoord(), horizontalCoord = from.getHorizontalCoord();  verticalCoord != to.getVerticalCoord() + incrementVertical &&
                horizontalCoord != to.getHorizontalCoord() + incrementHorizontal; verticalCoord+=incrementVertical, horizontalCoord+=incrementHorizontal){

            Position currPos = new Position(verticalCoord, horizontalCoord);
            if(pieceToBeCaptured != null && currPos.isSamePosition(to) && okToCapture(currPos, pieceToBeMoved, pieceToBeCaptured, game)) return true;
            if(!game.isSquareEmpty(currPos) && game.getPieceAtPosition(currPos) != pieceToBeMoved){;
                return false;
            }
        }
        return true;
    }

    private static boolean okToCapture(Position current, Piece pieceToBeMoved, Piece pieceToBeCaptured, Game game){
        boolean isDifferentColor = !pieceToBeCaptured.getColor().equals(pieceToBeMoved.getColor());
        return !game.isSquareEmpty(current) && game.getPieceAtPosition(current) == pieceToBeCaptured && isDifferentColor;
    }
    private static String checkDirection(Position from, Position to){
        if(Math.abs(from.getVerticalCoord() - to.getVerticalCoord()) == Math.abs(from.getHorizontalCoord() - to.getHorizontalCoord())){
            return DIAGONAL;
        }
        if(from.getVerticalCoord() == to.getVerticalCoord()){
            return HORIZONTAL;
        }
        if(from.getHorizontalCoord() == to.getHorizontalCoord()){
            return VERTICAL;
        }
        return INVALID;
    }
}
