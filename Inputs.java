import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

/**
 * Helps reading inputs from files
 *
 * @author Nerijus
 */
final class Inputs {
    static String readString(String fileName) {
        try (InputStream input = Inputs.class.getResourceAsStream("inputs/" + fileName)) {
            if (input == null) {
                throw new IllegalStateException("Input not found: " + "inputs/" + fileName);
            }
            return new Scanner(input, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    static List<String> readStrings(String fileName) {
        return Arrays.stream(readString(fileName).split("\n"))
                .collect(toList());
    }

    static List<Integer> readInts(String fileName) {
        return Arrays.stream(readString(fileName).split("\n"))
                .map(Integer::valueOf)
                .collect(toList());
    }

    static int[][] readIntMap(String fileName) {
        List<String> rows = readStrings(fileName);
        int[][] map = new int[rows.size()][rows.get(0).length()];
        for (int y = 0; y < rows.size(); y++) {
            String[] points = rows.get(y).split("");
            for (int x = 0; x < points.length; x++) {
                map[y][x] = Integer.parseInt(points[x]);
            }
        }
        return map;
    }
}
