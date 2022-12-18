import common.Coordinates;
import common.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/14">Day fourteen</a>
 *
 * @author Nerijus
 */
public class Day14_1 {
    public static void main(String[] args) {
        System.out.println("Units of sand: " + new Day14_1().getResult());
    }

    private int getResult() {
        char[][] map = getMap();

        Coordinates sandSource = new Coordinates(500,0);
        while (true) {
            if (!drop(sandSource, map)) {
                break;
            }
        }

        print(map);
        return countSand(map);
    }

    private boolean drop(Coordinates sandSource, char[][] map) {
        Coordinates current = sandSource;
        while (true) {
            if (current.y + 1 == map.length) {
                // fell to the bottom
                return false;
            }
            if (isStuck(current, map)) {
                map[current.y][current.x] = 'o';
                return true;
            }
            Coordinates bottom = current.top();
            if (isBlocked(bottom, map)) {
                // try left
                Coordinates bottomLeft = current.topLeft();
                if (isBlocked(bottomLeft, map)) {
                    // use bottom right then
                    current = current.topRight();
                } else {
                    current = bottomLeft;
                }
            } else {
                // fall more
                current = bottom;
            }
        }
    }

    private boolean isBlocked(Coordinates current, char[][] map) {
        return map[current.y][current.x] != 0;
    }

    private boolean isStuck(Coordinates current, char[][] map) {
        return Stream.of(current.top(), current.topRight(), current.topLeft())
                .map(c -> map[c.y][c.x])
                .allMatch(c -> c != 0);
    }

    private int countSand(char[][] map) {
        int count = 0;
        for (char[] row : map) {
            for (char c : row) {
                if (c == 'o') {
                    count += 1;
                }
            }
        }
        return count;
    }

    protected char[][] getMap() {
        List<Coordinates> allStones = Inputs.readStrings("Day14")
                .stream()
                .map(StonePath::new)
                .flatMap(p -> p.getPoints().stream())
                .toList();
        int maxY = allStones.stream().mapToInt(s -> s.y).max().getAsInt();
        char[][] map = new char[maxY + 10][600];
        allStones.forEach(p -> map[p.y][p.x] = '#');
        return map;
    }

    static class StonePath {
        List<StoneLine> path = new ArrayList<>();

        public StonePath(String source) {
            LinkedList<Coordinates> points = new LinkedList<>(Arrays.stream(source.split(" -> ")).map(p -> {
                String[] parts = p.split(",");
                return new Coordinates(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }).toList());
            for (int i = 0; i < points.size() - 1; i++) {
                path.add(new StoneLine(points.get(i), points.get(i + 1)));
            }
        }

        public Set<Coordinates> getPoints() {
            return path.stream()
                    .flatMap(p -> p.getPoints().stream())
                    .collect(Collectors.toSet());
        }
    }

    static class StoneLine extends Vector {
        public StoneLine(Coordinates from, Coordinates to) {
            super(from, to);
        }
    }

    void print(char[][] map) {
        for (char[] row : map) {
            for (char c : row) {
                System.out.print(c == 0 ? "." : c);
            }
            System.out.println();
        }
    }
}