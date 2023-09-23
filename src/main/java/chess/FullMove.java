package chess;

public class FullMove {
    private Move whiteMove;
    private Move blackMove;

    public FullMove(){}

    public FullMove(Move whiteMove){
        this.whiteMove = whiteMove;
        this.blackMove = null;
    }

    public Move getWhiteMove() {
        return whiteMove;
    }

    public Move getBlackMove() {
        return blackMove;
    }

    public void setBlackMove(Move blackMove) {
        this.blackMove = blackMove;
    }
}
