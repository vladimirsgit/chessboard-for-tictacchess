package chess.pieces.helpers;

import chess.Game;
import chess.Position;
import chess.pieces.Piece;

import java.util.HashMap;
import java.util.HashSet;

public class PawnHelper {
    /**
     * Making sure that the pawn isnt trying to move backwords.
     *
     * @param from The initial position
     * @param to The wanted position
     * @param game The current state of the game
     * @return It returns True if white tries to move to a smaller vertical coord than the initial one or if black tries to move to a bigger vertical coord than the initial one
     */
    //making sure that the pawn isnt trying to move backwards
    public static boolean triesToMoveBackwards(Position from, Position to, Game game) {
        Piece piece = game.getPieceAtPosition(from);
        String color = piece.getColor();
        int fromVerticalCoord = from.getVerticalCoord();
        int toVerticalCoord = to.getVerticalCoord();

        return (Piece.WHITE.equals(color) && fromVerticalCoord > toVerticalCoord)
                || (Piece.BLACK.equals(color) && fromVerticalCoord < toVerticalCoord);

    }
    /**
     * Checking to see if its the pawn's first move, will help to see if the double squares move is valid.
     *
     * @param from The initial position
     * @param piece The piece for which we want to check if its the first move
     * @return It returns true for white if 1st rank, true for black if 6th rank.
     */
    //checking if its the first move, will help to see if two squares move is valid
    public static boolean isFirstMove(Position from, Piece piece) {
        String color = piece.getColor();
        return (from.getVerticalCoord() == 2 && Piece.WHITE.equals(color)) || (from.getVerticalCoord() == 7
                && Piece.BLACK.equals(color));
    }

    public static boolean oneSquareForwardMove(Position from, Position to, Game game) {
        int verticalDistance = Math.abs(from.getVerticalCoord() - to.getVerticalCoord());
        boolean isHorizontalTheSame = from.getHorizontalCoord() == to.getHorizontalCoord();
        boolean isSquareFree = game.isSquareEmpty(to);

        return verticalDistance == 1 && isHorizontalTheSame && isSquareFree;
    }

    public static boolean twoSquaresForwardMove(Position from, Position to, Game game) {
        int verticalDistance = Math.abs(from.getVerticalCoord() - to.getVerticalCoord());
        boolean isHorizontalTheSame = from.getHorizontalCoord() == to.getHorizontalCoord();
        Piece pieceToBeMoved = game.getPieceAtPosition(from);

        boolean returnValue = isFirstMove(from, pieceToBeMoved) && verticalDistance == 2 && isHorizontalTheSame && isPathFreeForTwoSquaresMove(from, to, game);
        if(returnValue){
            Piece pawn = game.getPieceAtPosition(from);
            game.setEnPassantCandidate(pawn);
        }
        return returnValue;

    }
    /**
     * Checking if the 2 square move has free path.
     *
     * @param from The initial position
     * @param to The wanted position
     * @param game The current state of the game
     * @return It returns true if the wanted square and the one before are free
     */
    private static boolean isPathFreeForTwoSquaresMove(Position from, Position to, Game game) {
        Position intermediatePosition = new Position(from.getVerticalCoord() + 1, to.getHorizontalCoord());
        Piece piece = game.getPieceAtPosition(from);
        String pieceColor = piece.getColor();
        if(pieceColor.equals(Piece.BLACK)){
            intermediatePosition = new Position(from.getVerticalCoord() - 1, to.getHorizontalCoord());
        }
        return game.isSquareEmpty(to) && game.isSquareEmpty(intermediatePosition);
    }

    //is diag move if diff between coords is the same and the move is only one square
    public static boolean isDiagonalMove(Position from, Position to) {
        return (Math.abs(from.getVerticalCoord() - to.getVerticalCoord()) == Math.abs(from.getHorizontalCoord() - to.getHorizontalCoord())) && diagMoveIsOneSquare(from, to);
    }

    //making sure that the diag move is only one square
    private static boolean diagMoveIsOneSquare(Position from, Position to) {
        return Math.abs(from.getHorizontalCoord() - to.getHorizontalCoord()) == 1 && Math.abs(from.getVerticalCoord() - to.getVerticalCoord()) == 1;
    }

    /**
     * Trying to en passant.
     * First it gets the last pawn that the game set as en passantAble.
     * Next, we figure that the possible piece to be en passanted will have the same vertical coord (will be to the right or left of the piece trying to be moved) and the same
     * horizontal coord as the square it wants to move to.
     * @param from The initial position
     * @param to The wanted position
     * @param game The current state of the game
     * @return It returns true last enpassantAblePiece from game is the same as the piece the pawn is trying to get behind
     */
    public static boolean tryForEnPassant(Position from, Position to, Game game){

        Piece enPassantCandidate = game.getEnPassantCandidate();
        if(!diagMoveIsOneSquare(from, to) || enPassantCandidate == null){
            return false;
        }
        //we find out where the position of the last enpassantable piece might be, based on where the pawn is trying to move
        Position possiblePositionOfLastEnPassantablePiece = new Position(from.getVerticalCoord(), to.getHorizontalCoord());
        Piece possibleEnPassantablePiece = game.getPieceAtPosition(possiblePositionOfLastEnPassantablePiece);

        boolean enPassant = possibleEnPassantablePiece == enPassantCandidate;

        if(enPassant){
            game.getGameBoard()[possiblePositionOfLastEnPassantablePiece.getVerticalCoord()][possiblePositionOfLastEnPassantablePiece.getHorizontalCoord()] = null;
        }
        return enPassant;
    }

    public static void setPawnAttackedSquares(HashMap<Position, HashSet<String>> attackedSquares, Position pawnPosition, Piece pawn){
        Position leftAttackedSquare, rightAttackedSquare;
        if(pawn.getColor().equals(Piece.WHITE)){
            leftAttackedSquare = new Position(pawnPosition.getVerticalCoord() + 1, pawnPosition.getHorizontalCoord() - 1);
            rightAttackedSquare = new Position(pawnPosition.getVerticalCoord() + 1, pawnPosition.getHorizontalCoord() + 1);
        } else {
            leftAttackedSquare = new Position(pawnPosition.getVerticalCoord() - 1, pawnPosition.getHorizontalCoord() + 1);
            rightAttackedSquare = new Position(pawnPosition.getVerticalCoord() - 1, pawnPosition.getHorizontalCoord() - 1);
        }
        if(!leftAttackedSquare.isOutsideBoardBounds()){
            addSquare(leftAttackedSquare, attackedSquares, pawn);
        }
        if(!rightAttackedSquare.isOutsideBoardBounds()){
            addSquare(rightAttackedSquare, attackedSquares, pawn);
        }
    }
    private static void addSquare(Position pos, HashMap<Position, HashSet<String>> attackedSquares, Piece pawn){
        if(attackedSquares.containsKey(pos)){
            attackedSquares.get(pos).add(pawn.getColor());
        } else {
            HashSet<String> colors = new HashSet<>();
            colors.add(pawn.getColor());
            attackedSquares.put(pos, colors);
        }
    }
}