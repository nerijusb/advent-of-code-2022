/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/6">Day six</a>
 *
 * @author Nerijus
 */
public class Day06_2 extends Day06_1 {
    public static void main(String[] args) {
        System.out.println("Start-of-message marker: " + new Day06_2().getResult());
    }

    @Override
    protected int seek() {
        return 14;
    }
}
