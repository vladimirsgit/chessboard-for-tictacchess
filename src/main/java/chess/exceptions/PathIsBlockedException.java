package chess.exceptions;

public class PathIsBlockedException extends RuntimeException{
    public PathIsBlockedException(String message){
        super(message);
    }
}
