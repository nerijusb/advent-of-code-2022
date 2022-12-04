import java.util.List;
import java.util.Set;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/3">Day three</a>
 *
 * @author Nerijus
 */
public class Day03_2 extends Day03_1 {
    public static void main(String[] args) {
        System.out.println(": " + new Day03_2().getResult());
    }

    private int getResult() {
        int result = 0;
        List<Rucksack> rucksacks = getRucksacks();
        for (int i = 0; i < rucksacks.size(); i = i + 3) {
            result = result + priority(findCommonIn(List.of(rucksacks.get(i), rucksacks.get(i+1), rucksacks.get(i+2))));
        }
        return result;
    }

    private String findCommonIn(List<Rucksack> sacks) {
        Set<String> common = sacks.get(0).commonWith(sacks.get(1));
        return common.stream().filter(i -> sacks.get(2).getItems().contains(i)).toList().get(0);
    }
}
