import chess.Game;
import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Piece;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();



        game.movePieceAtCertainPosition(new Position(2, 1), new Position(2, 2));
        game.setAttackedSquares();
        game.printGameBoard();
        game.printAttackedSquares();


    }
}