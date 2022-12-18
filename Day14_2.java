/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/14">Day fourteen</a>
 *
 * @author Nerijus
 */
public class Day14_2 extends Day14_1 {
    public static void main(String[] args) {
        System.out.println("Units of sand: " + new Day14_2().getResult());
    }

    @Override
    protected char[][] getMap() {
        char[][] map = super.getMap();
        // add floor
        int floorY = map.length - 1;
        for (int x = 0; x < map[0].length; x++) {
            map[floorY][x] = '#';
        }
        return map;
    }

    @Override
    protected int maxX() {
        return 750;
    }
}
