import java.util.LinkedList;
import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/5">Day five</a>
 *
 * @author Nerijus
 */
public class Day05_2 extends Day05_1 {
    public static void main(String[] args) {
        System.out.println("Crates on top of each stack: " + new Day05_2().getResult());
    }

    @Override
    protected void apply(List<LinkedList<String>> stacks, Instruction instruction) {
        LinkedList<String> stackFrom = stacks.get(instruction.from - 1);
        LinkedList<String> stackTo = stacks.get(instruction.to - 1);
        LinkedList<String> toMove = new LinkedList<>();
        for (int i = 0; i < instruction.amount; i++)
        {
            String value = stackFrom.pop();
            toMove.push(value);
        }

        toMove.forEach(stackTo::push);
    }
}
