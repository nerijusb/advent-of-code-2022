import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/13">Day thirteen</a>
 *
 * @author Nerijus
 */
public class Day13_1 {
    public static void main(String[] args) {
        System.out.println("Sum of the indices of pairs in right order: " + new Day13_1().getResult());
    }

    private int getResult() {
        return readPacketPairs()
                .stream()
                .filter(PacketPair::isInRightOrder)
                .mapToInt(p -> p.index)
                .sum();
    }

    protected List<PacketPair> readPacketPairs() {
        LinkedList<String> input = new LinkedList<>(Inputs.readStrings("Day13"));
        List<PacketPair> packetPairs = new ArrayList<>();
        int index = 0;
        while (true) {
            index += 1;
            String left = input.pop();
            String right = input.pop();
            if (!input.isEmpty()) {
                // remove separator
                input.pop();
            }
            packetPairs.add(new PacketPair(index, new Packet(left, false), new Packet(right, false)));
            if (input.isEmpty()) {
                return packetPairs;
            }
        }
    }

    static class PacketPair {
        int index;
        Packet left;
        Packet right;
        PacketPair(int index, Packet left, Packet right) {
            this.index = index;
            this.left = left;
            this.right = right;
        }

        boolean isInRightOrder() {
            return left.compareTo(right) < 0;
        }
    }

    static class Packet implements Comparable<Packet> {
        List<Object> data;
        boolean separator;

        public Packet(String source, boolean separator) {
            this.data = read(source);
            this.separator = separator;
        }

        private List<Object> read(String source) {
            LinkedList<String> chars = new LinkedList<>(Arrays.stream(source.split("")).toList());
            LinkedList<List<Object>> openLists = new LinkedList<>();
            while (!chars.isEmpty()) {
                String nextPart = chars.pop();
                if ("[".equals(nextPart)) {
                    // start of collection
                    openLists.push(new ArrayList<>());
                } else if ("]".equals(nextPart)) {
                    // end of collection
                    if (openLists.size() != 1) {
                        List<Object> current = openLists.pop();
                        openLists.peek().add(current);
                    }
                } else if (",".equals(nextPart)) {
                    // nothing to do
                    continue;
                } else {
                    // peek for double-digit number
                    if (isNumber(chars.peek())) {
                        nextPart = nextPart + chars.pop();
                    }
                    List<Object> peek = openLists.peek();
                    if (peek != null) {
                        peek.add(Integer.parseInt(nextPart));
                    }
                }
            }
            return openLists.get(0);
        }

        boolean isNumber(String input) {
            return !List.of("[", "]", ",").contains(input);
        }

        int compare(Object left, Object right) {
            if (left instanceof Integer leftInt && right instanceof Integer rightInt) {
                // both numbers
                return leftInt.compareTo(rightInt);
            } else if (left instanceof List<?> leftList && right instanceof List<?> rightList) {
                if (leftList.isEmpty() && rightList.isEmpty()) {
                    return 0;
                }
                // both lists
                for (int i = 0; i < leftList.size(); i++) {
                    if (rightList.size() <= i) {
                        return 1;
                    }
                    int compareValue = compare(leftList.get(i), rightList.get(i));
                    if (compareValue != 0) {
                        return compareValue;
                    }
                }
            } else {
                // mixed types
                var leftMixed = left instanceof Integer ? List.of(left) : left;
                var rightMixed = right instanceof Integer ? List.of(right) : right;

                int compareValue = compare(leftMixed, rightMixed);
                if (compareValue != 0) {
                    return compareValue;
                }
            }
            return -1;
        }

        @Override
        public int compareTo(Packet o) {
            return compare(this.data, o.data);
        }
    }
}