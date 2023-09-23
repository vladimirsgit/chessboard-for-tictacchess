package chess.exceptions;

public class OutOfBoardBoundsException extends RuntimeException{
    public OutOfBoardBoundsException(String message){
        super(message);
    }
}
