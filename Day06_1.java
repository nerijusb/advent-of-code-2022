import java.util.HashSet;
import java.util.LinkedList;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/6">Day six</a>
 *
 * @author Nerijus
 */
public class Day06_1 {
    public static void main(String[] args) {
        System.out.println("Start-of-packet marker: " + new Day06_1().getResult());
    }

    protected int getResult() {
        String buffer = Inputs.readString("Day06");
        String[] chars = buffer.split("");
        LinkedList<String> read = new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            read.add(chars[i]);
            if (read.size() == seek()) {
                if (new HashSet<>(read).size() == seek()) {
                    return i + 1;
                } else {
                    read.removeFirst();
                }
            }
        }
        throw new IllegalStateException("Not found");
    }

    protected int seek() {
        return 4;
    }
}
