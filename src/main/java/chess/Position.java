package chess;

public class Position {
    private int verticalCoord;
    private int horizontalCoord;

    public Position(int verticalCoord, int horizontalCoord){
        this.verticalCoord = verticalCoord;
        this.horizontalCoord = horizontalCoord;
    }

    public int getVerticalCoord() {
        return verticalCoord;
    }

    public void setVerticalCoord(int verticalCoord) {
        this.verticalCoord = verticalCoord;
    }

    public int getHorizontalCoord() {
        return horizontalCoord;
    }

    public void setHorizontalCoord(int horizontalCoord) {
        this.horizontalCoord = horizontalCoord;
    }

    public boolean isSamePosition(Position to){
        return to.horizontalCoord == this.horizontalCoord && to.verticalCoord == this.verticalCoord;
    }
}
