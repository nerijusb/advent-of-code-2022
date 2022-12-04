package common;

public class Vector {
    public Coordinates from;
    public Coordinates to;

    public Vector(int x1, int y1, int x2, int y2) {
        from = new Coordinates(x1, y1);
        to = new Coordinates(x2, y2);
    }

    public boolean isHorizontal() {
        return from.isSameY(to);
    }

    public boolean isVertical() {
        return from.isSameX(to);
    }

    public boolean isHorizontalOrVertical() {
        return isHorizontal() || isVertical();
    }

    public boolean isDiagonal() {
        return isHorizontalOrVertical();
    }

    @Override
    public String toString() {
        return "(" + from.x + "," + from.y
                + ") -> (" +
                to.x + "," + to.y + ")";
    }
}