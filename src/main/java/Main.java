import chess.Game;
import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Piece;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        game.printGameBoard();

        game.setAttackedSquares();
        game.printAttackedSquares();


    }
}