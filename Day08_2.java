import common.Direction;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/8">Day eight</a>
 *
 * @author Nerijus
 */
public class Day08_2 extends Day08_1 {
    public static void main(String[] args) {
        System.out.println("Highest scenic score possible: " + new Day08_2().getResult());
    }

    private int getResult() {
        TreeMap treeMap = readMap();
        return treeMap.trees().stream()
                .mapToInt(t -> scenicScore(t, treeMap))
                .max()
                .orElseThrow();
    }

    private int scenicScore(Tree tree, TreeMap treeMap) {
        return countVisibleTrees(tree, treeMap, Direction.LEFT)
                * countVisibleTrees(tree, treeMap, Direction.UP)
                * countVisibleTrees(tree, treeMap, Direction.RIGHT)
                * countVisibleTrees(tree, treeMap, Direction.DOWN);
    }

    private int countVisibleTrees(Tree tree, TreeMap treeMap, Direction direction) {
        int visibleTrees = 0;
        Tree neighborTree = treeMap.getNeighborTree(tree, direction);
        while (neighborTree != null) {
            visibleTrees += 1;
            if (neighborTree.height() >= tree.height()) {
                return visibleTrees;
            } else {
                neighborTree = treeMap.getNeighborTree(neighborTree, direction);
            }
        }
        return visibleTrees;
    }
}
