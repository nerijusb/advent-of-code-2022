import common.Coordinates;
import common.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/9">Day nine</a>
 *
 * @author Nerijus
 */
public class Day09_1 {
    public static void main(String[] args) {
        System.out.println("Tail of the rope visit at least once: " + new Day09_1().getResult());
    }

    private int getResult() {
        Rope rope = new Rope(2);
        List<Instruction> instructions = readInstructions();
        instructions.forEach(rope::move);
        return rope.tailVisits.size();
    }

    protected List<Instruction> readInstructions() {
        return Inputs.readStrings("Day09").stream().map(i -> {
            String[] parts = i.split(" ");
            return new Instruction(switch (parts[0]) {
                case "U" -> Direction.UP;
                case "D" -> Direction.DOWN;
                case "R" -> Direction.RIGHT;
                case "L" -> Direction.LEFT;
                default -> throw new IllegalStateException("Unknown direction");
            }, Integer.parseInt(parts[1]));
        }).toList();
    }

    static class Rope {
        List<Coordinates> knots;
        Set<Coordinates> tailVisits = new HashSet<>(Set.of(new Coordinates(0, 0)));

        public Rope(int knots) {
            this.knots = new ArrayList<>();
            for (int i = 0; i < knots; i++) {
                this.knots.add(new Coordinates(0, 0));
            }
        }

        void move(Instruction instruction) {
            for (int i = 0; i < instruction.steps; i++) {
                moveHead(instruction.direction);
                pullTail();
            }
        }

        private void pullTail() {
            for (int i = 0; i < knots.size(); i++) {
                if (i == 0) {
                    // it's head
                    continue;
                }

                Coordinates previous = knots.get(i - 1);
                Coordinates knot = knots.get(i);

                if (previous.isSame(knot) || previous.getAllAdjacentAndDiagonal().contains(knot)) {
                    // is touching, nothing to do
                    continue;
                }

                List<Coordinates> headAdjacent = previous.getAllAdjacent();
                List<Coordinates> tailAdjacent = knot.getAllAdjacent();
                if (tailAdjacent.stream().anyMatch(headAdjacent::contains)) {
                    // prefer adjacent first
                    Coordinates newKnot = tailAdjacent.stream().filter(headAdjacent::contains).findFirst().orElseThrow();
                    knots.get(i).x = newKnot.x;
                    knots.get(i).y = newKnot.y;
                } else {
                    // otherwise use diagonal
                    List<Coordinates> tailDiagonal = knot.getAllDiagonal();
                    List<Coordinates> prevAll = previous.getAllAdjacentAndDiagonal();
                    Coordinates newKnot = tailDiagonal.stream().filter(prevAll::contains).findFirst().orElseThrow();
                    knots.get(i).x = newKnot.x;
                    knots.get(i).y = newKnot.y;
                }
            }
            tailVisits.add(new Coordinates(knots.get(knots.size() - 1))); // add tail as visit
        }

        private void moveHead(Direction direction) {
            Coordinates headPosition = knots.get(0).getAdjacent(direction);
            knots.remove(0);
            knots.add(0, headPosition);
        }
    }

    record Instruction(Direction direction, int steps) {
    }
}
