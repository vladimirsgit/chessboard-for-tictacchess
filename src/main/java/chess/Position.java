package chess;

import java.util.Objects;

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
    public boolean isOutsideBoardBounds(){
        return verticalCoord > 8 || verticalCoord < 1 || horizontalCoord > 8 || horizontalCoord < 1;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return verticalCoord == position.verticalCoord && horizontalCoord == position.horizontalCoord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticalCoord, horizontalCoord);
    }
}
