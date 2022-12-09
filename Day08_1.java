import common.Coordinates;
import common.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/8">Day eight</a>
 *
 * @author Nerijus
 */
public class Day08_1 {
    public static void main(String[] args) {
        System.out.println("Trees visible from outside the grid: " + new Day08_1().getResult());
    }

    private int getResult() {
        TreeMap treeMap = readMap();
        return treeMap
                .trees
                .stream()
                .mapToInt(t -> treeMap.isVisible(t) ? 1 : 0)
                .sum();
    }

    protected TreeMap readMap() {
        List<Tree> allTrees = new ArrayList<>();
        List<String> rows = Inputs.readStrings("Day08");
        for (int y = 0; y < rows.size(); y++) {
            String row = rows.get(y);
            String[] trees = row.split("");
            for (int x = 0; x < trees.length; x++) {
                int treeHeight = Integer.parseInt(trees[x]);
                allTrees.add(new Tree(new Coordinates(x, y), treeHeight));
            }
        }
        return new TreeMap(allTrees, rows.size(), rows.get(0).length());
    }

    record TreeMap(List<Tree> trees, int height, int width) {
        public boolean isVisible(Tree tree) {
            return isOnEdge(tree)
                    || isVisibleFrom(tree, Direction.LEFT)
                    || isVisibleFrom(tree, Direction.UP)
                    || isVisibleFrom(tree, Direction.RIGHT)
                    || isVisibleFrom(tree, Direction.DOWN);
        }

        public boolean isVisibleFrom(Tree tree, Direction fromDirection) {
            Tree currentNeighbor = getNeighborTree(tree, fromDirection);
            while (currentNeighbor != null) {
                if (currentNeighbor.height >= tree.height) {
                    return false;
                } else {
                    currentNeighbor = getNeighborTree(currentNeighbor, fromDirection);
                }
            }
            return true;
        }

        public Tree getNeighborTree(Tree tree, Direction direction) {
            Coordinates neighborPosition = tree.position.getValidAdjacent(direction, width, height);
            if (neighborPosition == null) {
                // off the edge
                return null;
            } else {
                return trees.stream()
                        .filter(t -> t.position.isSame(neighborPosition))
                        .findFirst()
                        .orElseThrow();
            }
        }

        public boolean isOnEdge(Tree tree) {
            return tree.position.x == 0
                    || tree.position.y == 0
                    || tree.position.x == width - 1
                    || tree.position.y == height - 1;
        }
    }

    record Tree(Coordinates position, int height) {
    }
}
