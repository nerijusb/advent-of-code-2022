import common.Coordinates;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/15">Day fifteen</a>
 *
 * @author Nerijus
 */
public class Day15_1 {
    public static final Pattern SENSOR_PATTERN = Pattern.compile("Sensor at x=(?<sx>-?\\d+), y=(?<sy>-?\\d+): closest beacon is at x=(?<bx>-?\\d+), y=(?<by>-?\\d+)");

    public static void main(String[] args) {
        System.out.println("Positions cannot contain a beacon: " + new Day15_1().getResult());
    }

    private int getResult() {
        List<Sensor> sensors = getSensors();
        int minX = getMinX(sensors);
        int maxX = getMaxX(sensors);
        int maxDistance = getMaxDistance(sensors);

        int result = 0;
        for (int x = minX - maxDistance; x < maxX + maxDistance; x++) {
            Coordinates p = new Coordinates(x, 2000000);
            if (sensors.stream().anyMatch(sensor -> !sensor.canContainBeacon(p))) {
                result += 1;
            }
        }

        return result;
    }

    protected List<Sensor> getSensors() {
        return Inputs.readStrings("Day15")
                .stream()
                .map(Sensor::new)
                .toList();
    }

    private static int getMaxDistance(List<Sensor> sensors) {
        return sensors
                .stream()
                .mapToInt(s -> s.beaconDistance)
                .max()
                .orElseThrow();
    }

    private static int getMaxX(List<Sensor> sensors) {
        return sensors
                .stream()
                .flatMapToInt(s -> IntStream.of(s.position.x, s.beaconPosition.x))
                .max()
                .orElseThrow();
    }

    private static int getMinX(List<Sensor> sensors) {
        return sensors
                .stream()
                .flatMapToInt(s -> IntStream.of(s.position.x, s.beaconPosition.x))
                .min()
                .orElseThrow();
    }

    static class Sensor {
        Coordinates position;
        Coordinates beaconPosition;
        int beaconDistance;

        public Sensor(String source) {
            Matcher matcher = SENSOR_PATTERN.matcher(source);
            if (matcher.find()) {
                position = new Coordinates(
                        Integer.parseInt(matcher.group("sx")),
                        Integer.parseInt(matcher.group("sy")));
                beaconPosition = new Coordinates(
                        Integer.parseInt(matcher.group("bx")),
                        Integer.parseInt(matcher.group("by")));
                beaconDistance = position.distanceTo(beaconPosition);
            } else {
                throw new IllegalStateException("Could not parse input");
            }
        }

        boolean canContainBeacon(Coordinates possiblePosition) {
            if (possiblePosition.equals(beaconPosition)) {
                return true;
            }
            return position.distanceTo(possiblePosition) > beaconDistance;
        }

        boolean hasSensorOrBeacon(Coordinates possiblePosition) {
            return position.equals(possiblePosition) || beaconPosition.equals(possiblePosition);
        }
    }
}