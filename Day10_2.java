import java.util.LinkedList;
import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/10">Day ten</a>
 *
 * @author Nerijus
 */
public class Day10_2 extends Day10_1 {
    public static void main(String[] args) {
        System.out.println("Eight capital letters appear on CRT: \n");
        new Day10_2().getResult();
    }

    private void getResult() {
        LinkedList<String> ops = new LinkedList<>(Inputs.readStrings("Day10"));
        StringBuilder crt = new StringBuilder();
        int spritePosition = 1;
        String currentOp = null;
        for (int line = 0; line < 6; line++) {
            for (int cycle = 1; cycle <= 40; cycle++) {
                crt.append(isLit(cycle, spritePosition) ? '#' : '.');
                if (currentOp == null) {
                    String nextOp = ops.pop();
                    if (!"noop".equals(nextOp)) {
                        currentOp = nextOp;
                    }
                } else {
                    // a two cycle op in progress
                    String[] parts = currentOp.split(" ");
                    int increment = Integer.parseInt(parts[1]);
                    spritePosition += increment;
                    currentOp = null;
                }
            }
        }

        // print
        String[] pixels = crt.toString().split("");
        List<Integer> lineBreaks = List.of(40, 80, 120, 160, 200, 240);
        for (int i = 0; i < pixels.length; i++) {
            if (lineBreaks.contains(i)) {
                System.out.print("\n");
            }
            System.out.print(pixels[i]);
        }
    }

    private boolean isLit(int cycle, int spritePosition) {
        int pixelPosition = cycle - 1;
        return pixelPosition == spritePosition ||
                pixelPosition == spritePosition - 1 ||
                pixelPosition == spritePosition + 1;
    }
}
