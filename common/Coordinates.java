package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates source) {
        this.x = source.x;
        this.y = source.y;
    }

    public boolean isSame(Coordinates coordinates) {
        return isSameY(coordinates) && isSameX(coordinates);
    }

    public boolean isSameY(Coordinates coordinates) {
        return Integer.valueOf(coordinates.y).equals(y);
    }

    public boolean isSameX(Coordinates coordinates) {
        return Integer.valueOf(coordinates.x).equals(x);
    }

    public Coordinates getAdjacent(Direction direction) {
        return switch (direction) {
            case UP -> top();
            case DOWN -> bottom();
            case LEFT -> left();
            case RIGHT -> right();
        };
    }

    public Coordinates getValidAdjacent(Direction direction, int maxX, int maxY) {
        var coordinates = switch (direction) {
            case UP -> top();
            case DOWN -> bottom();
            case LEFT -> left();
            case RIGHT -> right();
        };
        return isValid(coordinates, maxX, maxY) ? coordinates : null;
    }

    public List<Coordinates> getAllAdjacent() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            coordinates.add(getAdjacent(direction));
        }
        return coordinates;
    }

    public List<Coordinates> getAllValidAdjacent(int maxX, int maxY) {
        return getAllAdjacent()
                .stream()
                .filter(c -> isValid(c, maxX, maxY))
                .collect(Collectors.toList());
    }

    public boolean isValid(Coordinates c, int maxX, int maxY) {
        return c.x >= 0 && c.y >= 0 && c.x < maxX && c.y < maxY;
    }

    public List<Coordinates> getAllAdjacentAndDiagonal() {
        List<Coordinates> coordinates = getAllAdjacent();
        coordinates.add(topLeft());
        coordinates.add(topRight());
        coordinates.add(bottomLeft());
        coordinates.add(bottomRight());
        return coordinates;
    }

    public List<Coordinates> getAllDiagonal() {
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(topLeft());
        coordinates.add(topRight());
        coordinates.add(bottomLeft());
        coordinates.add(bottomRight());
        return coordinates;
    }

    public List<Coordinates> getAllValidAdjacentAndDiagonal(int maxX, int maxY) {
        return getAllAdjacentAndDiagonal()
                .stream()
                .filter(c -> isValid(c, maxX, maxY))
                .collect(Collectors.toList());
    }

    public Coordinates right() {
        return new Coordinates(x + 1, y);
    }

    public Coordinates left() {
        return new Coordinates(x - 1, y);
    }

    public Coordinates bottom() {
        return new Coordinates(x, y - 1);
    }

    public Coordinates top() {
        return new Coordinates(x, y + 1);
    }

    public Coordinates bottomRight() {
        return new Coordinates(x + 1, y - 1);
    }

    public Coordinates bottomLeft() {
        return new Coordinates(x - 1, y - 1);
    }

    public Coordinates topRight() {
        return new Coordinates(x + 1, y + 1);
    }

    public Coordinates topLeft() {
        return new Coordinates(x - 1, y + 1);
    }

    public int distanceTo(Coordinates to) {
        return Math.abs(to.x - this.x) + Math.abs(to.y - this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ')';
    }
}
