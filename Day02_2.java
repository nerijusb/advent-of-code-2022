/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/2">Day two</a>
 *
 * @author Nerijus
 */
public class Day02_2 extends Day02_1 {
    public static void main(String[] args) {
        System.out.println("Total score: " + new Day02_2().getResult());
    }

    private int getResult() {
        return getPlays()
                .stream()
                .mapToInt(this::score)
                .sum();
    }

    int score(String hand) {
        return switch (hand) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> throw new IllegalStateException("Unexpected hand");
        };
    }

    String loosingHand(String hand) {
        return switch (hand) {
            case "A" -> "C";
            case "B" -> "A";
            default -> "B";
        };
    }

    String winningHand(String hand) {
        return switch (hand) {
            case "A" -> "B";
            case "B" -> "C";
            default -> "A";
        };
    }

    int score(Play play) {
        int score = 0;
        switch (play.getMine()) {
            case "X" -> score += score(loosingHand(play.getOpponent()));
            case "Y" -> {
                score += score(play.getOpponent());
                score += 3;
            }
            case "Z" -> {
                score += score(winningHand(play.getOpponent()));
                score += 6;
            }
        }

        return score;
    }
}
