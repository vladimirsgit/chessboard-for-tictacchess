import chess.Game;
import chess.Position;
import chess.pieces.Piece;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        game.printGameBoard();

        Position from = new Position(7, 2);
        Position to = new Position(5, 2);

        Piece piece = game.getPieceAtPosition(from);

        piece.move(from, to, game);

        game.printGameBoard();



    }
}