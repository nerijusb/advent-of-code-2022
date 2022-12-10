import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/9">Day nine</a>
 *
 * @author Nerijus
 */
public class Day09_2 extends Day09_1 {
    public static void main(String[] args) {
        System.out.println("Tail of the rope visit at least once: " + new Day09_2().getResult());
    }

    private int getResult() {
        Rope rope = new Rope(10);
        List<Instruction> instructions = readInstructions();
        instructions.forEach(rope::move);
        return rope.tailVisits.size();
    }
}
