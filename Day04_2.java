/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/4">Day four</a>
 *
 * @author Nerijus
 */
public class Day04_2 extends Day04_1 {
    public static void main(String[] args) {
        System.out.println("Pairs that overlap: " + new Day04_2().getResult());
    }

    private int getResult() {
        return getAssignments()
                .stream()
                .filter(Assignment::overlaps)
                .toList()
                .size();
    }
}
