import common.Coordinates;
import common.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/16">Day sixteen</a>
 *
 * @author Nerijus
 */
public class Day16_1 {
    public static void main(String[] args) {
        System.out.println("Tower height after 2022 rocks: " + new Day16_1().getResult());
    }

    private int getResult() {
        Jet jet = new Jet(Inputs.readString("Day16"));
        List<Rock> rocks = List.of(new HorizontalRock(), new CrossRock(), new LRock(), new VerticalRock(), new SquareRock());
        List<Coordinates> pattern = new ArrayList<>();
        for (int i = 0; i < 2022; i++) {
            Rock rock = rocks.get(i % 5);
            int maxY = maxY(pattern);
            drop(rock.create(new Coordinates(3, maxY + 3 + rock.height())), pattern, jet);
        }
        print(pattern);
        return maxY(pattern);
    }


    private void drop(Rock rock, List<Coordinates> pattern, Jet jet) {
        while (true) {
            // push
            Direction jetDir = jet.next();
            if (rock.canMove(jetDir, pattern)) {
                // move in that direction
                rock.move(jetDir);
            }
            // drop
            if (!rock.canMove(Direction.DOWN, pattern)) {
                // update pattern
                pattern.addAll(rock.position);
                return;
            } else {
                rock.move(Direction.DOWN);
            }
        }
    }

    private int maxY(List<Coordinates> pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        return pattern.stream().mapToInt(c -> c.y).max().orElseThrow();
    }

    private void print(List<Coordinates> pattern) {
        int maxY = maxY(pattern);
        for (int y = maxY + 1; y >= 0; y--) {
            for (int x = 0; x < 9; x++) {
                if (pattern.contains(new Coordinates(x, y))) {
                    System.out.print("#");
                } else if ((x == 0 && y == 0) || (x == 8 && y == 0)) {
                    System.out.print("+");
                } else if (x == 0 || x == 8) {
                    System.out.print("|");
                } else if (y == 0) {
                    System.out.print("-");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    abstract static class Rock {
        List<Coordinates> position = new ArrayList<>();

        abstract int height();
        abstract Rock create(Coordinates start);

        public boolean canMove(Direction dir, List<Coordinates> current) {
            // calculate next possible position
            List<Coordinates> next = newPosition(dir);
            // lowest y != 0 and
            return next.stream().noneMatch(p -> p.y == 0)
                    // x != 0 and x != 8
                    && next.stream().noneMatch(p -> p.x == 0 || p.x == 8)
                    // new position does not intersect any existing
                    && next.stream().noneMatch(np -> current.stream().anyMatch(np::equals));
        }

        public void move(Direction dir) {
            position = newPosition(dir);
        }

        private List<Coordinates> newPosition(Direction dir) {
            return position.stream().map(p -> p.getAdjacent(dir)).toList();
        }

        @Override
        public String toString() {
            return position.stream()
                    .map(Coordinates::toString)
                    .collect(Collectors.joining(","));
        }
    }

    static class HorizontalRock extends Rock {
        @Override
        public int height() {
            return 1;
        }

        @Override
        public HorizontalRock create(Coordinates start) {
            HorizontalRock rock = new HorizontalRock();
            rock.position.add(start);
            rock.position.add(rock.position.get(0).right());
            rock.position.add(rock.position.get(1).right());
            rock.position.add(rock.position.get(2).right());
            return rock;
        }
    }

    static class CrossRock extends Rock {
        @Override
        public int height() {
            return 3;
        }

        @Override
        public CrossRock create(Coordinates start) {
            CrossRock rock = new CrossRock();
            rock.position.add(start.right());
            rock.position.add(rock.position.get(0).bottom());
            rock.position.add(rock.position.get(1).left());
            rock.position.add(rock.position.get(1).right());
            rock.position.add(rock.position.get(1).bottom());
            return rock;
        }
    }

    static class LRock extends Rock {
        @Override
        public int height() {
            return 3;
        }

        @Override
        public LRock create(Coordinates start) {
            LRock rock = new LRock();
            rock.position.add(start.right().right());
            rock.position.add(rock.position.get(0).bottom());
            rock.position.add(rock.position.get(1).bottom());
            rock.position.add(rock.position.get(2).left());
            rock.position.add(rock.position.get(3).left());
            return rock;
        }
    }

    static class VerticalRock extends Rock {
        @Override
        public int height() {
            return 4;
        }

        @Override
        public VerticalRock create(Coordinates start) {
            VerticalRock rock = new VerticalRock();
            rock.position.add(start);
            rock.position.add(rock.position.get(0).bottom());
            rock.position.add(rock.position.get(1).bottom());
            rock.position.add(rock.position.get(2).bottom());
            return rock;
        }
    }

    static class SquareRock extends Rock {
        @Override
        public int height() {
            return 2;
        }

        @Override
        public VerticalRock create(Coordinates start) {
            VerticalRock rock = new VerticalRock();
            rock.position.add(start);
            rock.position.add(rock.position.get(0).right());
            rock.position.add(rock.position.get(1).bottom());
            rock.position.add(rock.position.get(2).left());
            return rock;
        }
    }

    static class Jet {
        LinkedList<Direction> directions;

        Jet(String source) {
            directions = new LinkedList<>(Arrays.stream(source.split("")).map(d -> switch (d) {
                case "<": yield Direction.LEFT;
                case ">": yield Direction.RIGHT;
                default: throw new IllegalStateException("Unknown direction");
            }).toList());
        }

        Direction next() {
            Direction next = directions.pop();
            directions.addLast(next);
            return next;
        }
    }
}