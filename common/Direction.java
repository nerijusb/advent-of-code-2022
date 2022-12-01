package common;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public Direction rotate(boolean left) {
        return switch (this) {
            case UP -> left ? LEFT : RIGHT;
            case DOWN -> left ? RIGHT : LEFT;
            case LEFT -> left ? DOWN : UP;
            case RIGHT -> left ? UP : DOWN;
        };
    }

    public Direction rotateLeft() {
        return rotate(true);
    }

    public Direction rotateRight() {
        return rotate(false);
    }
}
