import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/1">Day one</a>
 *
 * @author Nerijus
 */
public class Day01_1 {
    public static void main(String[] args) {
        System.out.println("Max Calories Elf is carrying: " + new Day01_1().getResult());
    }

    private int getResult() {
        return getCalories()
                .mapToInt(i -> i)
                .max()
                .orElseThrow();
    }

    protected Stream<Integer> getCalories() {
        List<String> calories = Inputs.readStrings("Day01");
        int sum = 0;
        List<Integer> sums = new ArrayList<>();
        for (String item : calories) {
            if (item.isBlank()) {
                sums.add(sum);
                sum = 0;
            } else {
                sum += Integer.parseInt(item);
            }
        }
        return sums.stream();
    }
}
