package chess.exceptions;

public class BackwardsPawnMoveException extends RuntimeException{
    public BackwardsPawnMoveException(String message){
        super(message);
    }
}
