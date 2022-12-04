import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/2">Day two</a>
 *
 * @author Nerijus
 */
public class Day02_1 {
    public static void main(String[] args) {
        System.out.println("Total score: " + new Day02_1().getResult());
    }

    private int getResult() {
        return getPlays()
                .stream()
                .mapToInt(this::score)
                .sum();
    }

    protected List<Play> getPlays() {
        return Inputs.readStrings("Day02").stream()
                .map(p -> p.split(" "))
                .map(parts -> new Play(parts[0], parts[1]))
                .toList();
    }

    record Play(String opponent, String mine) {
        public String getOpponent() {
            return opponent;
        }

        public String getMine() {
            return mine;
        }

        boolean isDraw() {
            return (opponent.equals("A") && mine.equals("X"))
                    || (opponent.equals("B") && mine.equals("Y"))
                    || (opponent.equals("C") && mine.equals("Z"));
        }

        boolean isWin() {
            return (opponent.equals("A") && mine.equals("Y"))
                    || (opponent.equals("B") && mine.equals("Z"))
                    || (opponent.equals("C") && mine.equals("X"));
        }
    }

    int score(Play play) {
        int score = 0;
        switch (play.mine)
        {
            case "X" -> score += 1;
            case "Y" -> score += 2;
            case "Z" -> score += 3;
        }

        if (play.isDraw()) {
            score += 3;
        } else if (play.isWin()) {
            score += 6;
        }

        return score;
    }
}
