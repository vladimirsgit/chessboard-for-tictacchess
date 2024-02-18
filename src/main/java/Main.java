import chess.Game;
import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Piece;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        game.printGameBoard();

        Position from = new Position(7, 1);
        Position to = new Position(5, 1);

        Piece piece = game.getPieceAtPosition(from);

        piece.move(from, to, game);

        game.printGameBoard();


        from = new Position(5, 1);
        to = new Position(4, 1);
        piece = game.getPieceAtPosition(from);
        piece.move(from, to, game);
        
        game.printGameBoard();


    }
}