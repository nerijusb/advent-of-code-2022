import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/11">Day eleven</a>
 *
 * @author Nerijus
 */
public class Day11_1 {
    public static void main(String[] args) {
        System.out.println("Monkey business after 20 rounds: " + new Day11_1().getResult());
    }

    private int getResult() {
        // List<Monkey> monkeys = getTestMonkeys();
        List<Monkey> monkeys = getMonkeys();
        runKeepAway(monkeys, 20, true);

        List<Integer> collect = monkeys.stream().map(m -> m.inspections).sorted(Comparator.reverseOrder()).toList();
        return collect.get(0) * collect.get(1);
    }

    protected void runKeepAway(List<Monkey> monkeys, int rounds, boolean reduceWorry) {
        for (int round = 0; round < rounds; round++) {
            System.out.println("Round " + round);
            for (Monkey monkey : monkeys) {
                List<BigDecimal> items = monkey.items;
                for (BigDecimal item : new ArrayList<>(items)) {
                    monkey.inspect();
                    // worry level change
                    BigDecimal newItem = monkey.operation.apply(item);
                    if (reduceWorry) {
                        // gets bored
                        newItem = newItem.divideAndRemainder(new BigDecimal(3))[0];
                    }
                    // throw to another monkey
                    Integer recipient = monkey.test.apply(newItem);
                    monkeys.get(recipient).items.add(newItem);
                    monkey.items.remove(item);
                }
            }
        }
    }

    protected List<Monkey> getTestMonkeys() {
        return List.of(
                new Monkey(0,
                        List.of(79, 98),
                        old -> old.multiply(new BigDecimal(19)),
                        item -> isDivisible(item, 23) ? 2 : 3),
                new Monkey(1,
                        List.of(54, 65, 75, 74),
                        old -> old.add(new BigDecimal(6)),
                        item -> isDivisible(item, 19) ? 2 : 0),
                new Monkey(2,
                        List.of(79, 60, 97),
                        old -> old.multiply(old),
                        item -> isDivisible(item, 13) ? 1 : 3),
                new Monkey(3,
                        List.of(74),
                        old -> old.add(new BigDecimal(3)),
                        item -> isDivisible(item, 17) ? 0 : 1)
        );
    }

    protected List<Monkey> getMonkeys() {
        return List.of(
                new Monkey(0,
                        List.of(93, 98),
                        old -> old.multiply(new BigDecimal(17)),
                        item -> isDivisible(item, 19) ? 5 : 3),
                new Monkey(1,
                        List.of(95, 72, 98, 82, 86),
                        old -> old.add(new BigDecimal(5)),
                        item -> isDivisible(item, 13) ? 7 : 6),
                new Monkey(2,
                        List.of(85, 62, 82, 86, 70, 65, 83, 76),
                        old -> old.add(new BigDecimal(8)),
                        item -> isDivisible(item, 5) ? 3 : 0),
                new Monkey(3,
                        List.of(86, 70, 71, 56),
                        old -> old.add(new BigDecimal(1)),
                        item -> isDivisible(item, 7) ? 4 : 5),
                new Monkey(4,
                        List.of(77, 71, 86, 52, 81, 67),
                        old -> old.add(new BigDecimal(4)),
                        item -> isDivisible(item, 17) ? 1 : 6),
                new Monkey(5,
                        List.of(89, 87, 60, 78, 54, 77, 98),
                        old -> old.multiply(new BigDecimal(7)),
                        item -> isDivisible(item, 2) ? 1 : 4),
                new Monkey(6,
                        List.of(69, 65, 63),
                        old -> old.add(new BigDecimal(6)),
                        item -> isDivisible(item, 3) ? 7 : 2),
                new Monkey(7,
                        List.of(89),
                        old -> old.multiply(old),
                        item -> isDivisible(item, 11) ? 0 : 2)
        );
    }

    static boolean isDivisible(BigDecimal number, int divisor) {
        return number.divideAndRemainder(new BigDecimal(divisor))[1].equals(BigDecimal.ZERO);
    }

    static class Monkey {
        int number;
        List<BigDecimal> items;
        Function<BigDecimal, BigDecimal> operation;
        Function<BigDecimal, Integer> test;
        int inspections;

        public Monkey(int number,
                      List<Integer> items,
                      Function<BigDecimal, BigDecimal> operation,
                      Function<BigDecimal, Integer> test) {
            this.number = number;
            this.items = new ArrayList<>(items.stream().map(BigDecimal::new).toList());
            this.operation = operation;
            this.test = test;
        }

        void inspect() {
            inspections += 1;
        }
    }
}