import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/5">Day five</a>
 *
 * @author Nerijus
 */
public class Day05_1 {
    public static void main(String[] args) {
        System.out.println("Crates on top of each stack: " + new Day05_1().getResult());
    }

    protected String getResult() {
        List<LinkedList<String>> stacks = getStacks();

        List<Instruction> instructions = readInstructions();
        instructions.forEach(r -> apply(stacks, r));

        StringBuilder result = new StringBuilder();
        stacks.forEach(s -> result.append(s.poll()));
        return result.toString();
    }

    private List<Instruction> readInstructions() {
        return Inputs.readStrings("Day05_2")
                .stream()
                .map(Instruction::new)
                .toList();
    }

    private List<LinkedList<String>> getStacks() {
        List<LinkedList<String>> stacks = new ArrayList<>();
        stacks.add(new LinkedList<>(List.of("W", "T", "H", "P", "J", "C", "F")));
        stacks.add(new LinkedList<>(List.of("H", "B", "J", "Z", "F", "V", "R", "G")));
        stacks.add(new LinkedList<>(List.of("R", "T", "P", "H")));
        stacks.add(new LinkedList<>(List.of("T", "H", "P", "N", "S", "Z")));
        stacks.add(new LinkedList<>(List.of("D", "C", "J", "H", "Z", "F", "V", "N")));
        stacks.add(new LinkedList<>(List.of("Z", "D", "W", "F", "G", "M", "P")));
        stacks.add(new LinkedList<>(List.of("P", "D", "J", "S", "W", "Z", "V", "M")));
        stacks.add(new LinkedList<>(List.of("S", "D", "N")));
        stacks.add(new LinkedList<>(List.of("M", "F", "S", "Z", "D")));
        return stacks;
    }

    protected void apply(List<LinkedList<String>> stacks, Instruction instruction) {
        LinkedList<String> stackFrom = stacks.get(instruction.from - 1);
        LinkedList<String> stackTo = stacks.get(instruction.to - 1);
        for (int i = 0; i < instruction.amount; i++) {
            String value = stackFrom.pop();
            stackTo.push(value);
        }
    }

    static class Instruction {
        private static final Pattern INSTRUCTION_REGEX = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        int amount, from, to;

        public Instruction(String rule) {
            Matcher matcher = INSTRUCTION_REGEX.matcher(rule);
            if (matcher.find()) {
                amount = Integer.parseInt(matcher.group(1));
                from = Integer.parseInt(matcher.group(2));
                to = Integer.parseInt(matcher.group(3));
            } else {
                throw new IllegalStateException("Could not parse instruction");
            }
        }
    }
}
