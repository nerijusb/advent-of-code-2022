import java.util.*;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/3">Day three</a>
 *
 * @author Nerijus
 */
public class Day03_1 {
    public static void main(String[] args) {
        System.out.println("Sum of the priorities: " + new Day03_1().getResult());
    }

    private int getResult() {
        return getRucksacks()
                .stream()
                .mapToInt(r -> priority(r.getCommon()))
                .sum();
    }

    protected List<Rucksack> getRucksacks() {
        return Inputs.readStrings("Day03")
                .stream()
                .map(Rucksack::new)
                .toList();
    }

    protected int priority(String item) {
        // Java char codes
        // a - 97
        // z - 122
        // A - 65
        // Z - 90
        int itemCode = item.toCharArray()[0];
        if (itemCode >= 97) {
            itemCode = itemCode - 96;
        } else {
            itemCode = itemCode - 38;
        }

        return itemCode;
    }

    static class Rucksack {
        String source;
        List<String> leftCompartment;
        List<String> rightCompartment;

        public Rucksack(String source) {
            this.source = source;
            this.leftCompartment = Arrays.stream(source.substring(0, source.length() / 2).split("")).toList();
            this.rightCompartment = Arrays.stream(source.substring(source.length() / 2).split("")).toList();
        }

        String getCommon() {
            Set<String> common = new HashSet<>();
            rightCompartment.forEach(i -> {
                if (leftCompartment.contains(i)) {
                    common.add(i);
                }
            });
            leftCompartment.forEach(i -> {
                if (rightCompartment.contains(i)) {
                    common.add(i);
                }
            });
            return common.iterator().next();
        }

        Set<String> commonWith(Rucksack other) {
            return other.getItems()
                    .stream()
                    .filter(i -> getItems().contains(i))
                    .collect(Collectors.toSet());
        }

        List<String> getItems() {
            List<String> items = new ArrayList<>(leftCompartment);
            items.addAll(rightCompartment);
            return items;
        }
    }
}
