import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Part two of
 * <a href="https://adventofcode.com/2022/day/13">Day thirteen</a>
 *
 * @author Nerijus
 */
public class Day13_2 extends Day13_1 {
    public static void main(String[] args) {
        System.out.println("Decoder key for the distress signal: " + new Day13_2().getResult());
    }

    private int getResult() {
        Packet separatorOne = new Packet("[[2]]", true);
        Packet separatorTwo = new Packet("[[6]]", true);
        List<Packet> packets = new ArrayList<>(readPacketPairs()
                .stream()
                .flatMap(pair -> Stream.of(pair.left, pair.right))
                .toList());
        packets.add(separatorOne);
        packets.add(separatorTwo);
        Collections.sort(packets);
        return (packets.indexOf(separatorOne) + 1) * (packets.indexOf(separatorTwo) + 1);
    }
}
