package common;

public class Interval {
    int from, to;

    public Interval(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public boolean overlapsFully(Interval other) {
        if (isEqual(other)) {
            return true;
        }
        return from >= other.from && to <= other.to;
    }

    public boolean overlaps(Interval other) {
        if (isEqual(other)) {
            return true;
        }
        return (from >= other.from && from <= other.to)
                || (to <= other.to && to >= other.from);
    }

    public boolean isEqual(Interval other) {
        return from == other.from && to == other.to;
    }
}
