package common;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    public Coordinates from;
    public Coordinates to;

    public Vector(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    public Vector(int x1, int y1, int x2, int y2) {
        this.from = new Coordinates(x1, y1);
        this.to = new Coordinates(x2, y2);
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
        return !isHorizontalOrVertical();
    }
    
    public List<Coordinates> getPoints() {
        List<Coordinates> allPoints = new ArrayList<>();
        if (from.isSameX(to)) {
            int min = Math.min(from.y, to.y);
            int max = Math.max(from.y, to.y);
            for (int y = min; y <= max; y++) {
                allPoints.add(new Coordinates(from.x, y));
            }
        } else if (from.isSameY(to)) {
            int min = Math.min(from.x, to.x);
            int max = Math.max(from.x, to.x);
            for (int x = min; x <= max; x++) {
                allPoints.add(new Coordinates(x, from.y));
            }
        } else {
            throw new IllegalStateException("Not implemented");
        }
        
        return allPoints;
    }

    @Override
    public String toString() {
        return "(" + from.x + "," + from.y
                + ") -> (" +
                to.x + "," + to.y + ")";
    }
}