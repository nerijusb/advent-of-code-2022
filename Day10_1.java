import java.util.LinkedList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/10">Day ten</a>
 *
 * @author Nerijus
 */
public class Day10_1 {
    public static void main(String[] args) {
        System.out.println("Sum of six signal strengths: " + new Day10_1().getResult());
    }

    private int getResult() {
        LinkedList<String> ops = new LinkedList<>(Inputs.readStrings("Day10"));
        List<Integer> cycleNumbers = List.of(20, 60, 100, 140, 180, 220);
        int regX = 1;
        int signalStrength = 0;
        String currentOp = null;
        for (int cycle = 1; cycle <= 220; cycle++) {
            if (cycleNumbers.contains(cycle)) {
                signalStrength += cycle * regX;
            }
            if (currentOp == null) {
                String nextOp = ops.pop();
                if (!"noop".equals(nextOp)) {
                    currentOp = nextOp;
                }
            } else {
                // a two cycle op in progress
                String[] parts = currentOp.split(" ");
                int increment = Integer.parseInt(parts[1]);
                regX += increment;
                currentOp = null;
            }
        }

        return signalStrength;
    }
}
