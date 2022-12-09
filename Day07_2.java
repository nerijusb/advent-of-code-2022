/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/7">Day seven</a>
 *
 * @author Nerijus
 */
public class Day07_2 extends Day07_1 {
    public static void main(String[] args) {
        System.out.println("Directory size to delete: " + new Day07_2().getResult());
    }

    private int getResult() {
        Directory root = readFileStructure();
        int used = root.size();
        int free = 70000000 - used;
        int needed = 30000000 - free;
        return root
                .getAllDirectories()
                .stream()
                .mapToInt(Directory::size)
                .filter(size -> size >= needed)
                .min()
                .orElseThrow();
    }
}
