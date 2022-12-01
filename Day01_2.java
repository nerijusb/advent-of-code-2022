import static java.util.Comparator.reverseOrder;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/1">Day one</a>
 *
 * @author Nerijus
 */
public class Day01_2 extends Day01_1 {
    public static void main(String[] args) {
        System.out.println("Sum of top three calorie counts: " + new Day01_2().getResult());
    }

    private int getResult() {
        return getCalories()
                .sorted(reverseOrder())
                .mapToInt(i -> i)
                .limit(3)
                .sum();
    }
}
