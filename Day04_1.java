import common.Interval;

import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/4">Day four</a>
 *
 * @author Nerijus
 */
public class Day04_1 {
    public static void main(String[] args) {
        System.out.println("Pairs where range fully contain the other: " + new Day04_1().getResult());
    }

    private int getResult() {
        return getAssignments()
                .stream()
                .filter(Assignment::overlapsFully)
                .toList()
                .size();
    }

    protected List<Assignment> getAssignments() {
        return Inputs.readStrings("Day04")
                .stream()
                .map(Assignment::new)
                .toList();
    }

    static class Assignment {
        Interval elf1, elf2;

        Assignment(String source) {
            String[] parts = source.split(",");
            elf1 = toInterval(parts[0]);
            elf2 = toInterval(parts[1]);
        }

        private Interval toInterval(String interval) {
            String[] parts = interval.split("-");
            return new Interval(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        boolean overlapsFully() {
            return elf1.overlapsFully(elf2) || elf2.overlapsFully(elf1);
        }

        boolean overlaps() {
            return elf1.overlaps(elf2) || elf2.overlaps(elf1);
        }
    }
}
