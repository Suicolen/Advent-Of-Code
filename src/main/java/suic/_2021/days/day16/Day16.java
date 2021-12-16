package suic._2021.days.day16;

import one.util.streamex.LongStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;
import suic.util.StringUtils;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;

public class Day16 implements Puzzle<Long> {

    private BitReader reader;
    private long part1;
    private long part2;

    @Override
    public void parse() {
        String input = FileUtils.readResourceAsString(getClass().getSimpleName() + "Input.txt");
        byte[] bytes = HexFormat.of().parseHex(input);
        reader = new BitReader(bytes);
    }

    @Override
    public void init() {
        parse();
        part2 = evaluate(); // well.... this isn't ideal, will probably refactor later
    }

    @Override
    public Long solvePart1() {
        return part1;
    }

    @Override
    public Long solvePart2() {
        return part2;
    }

    private long evaluate() {
        part1 += readBits(3); // version
        return switch (readBits(3)) { // type
            case 0 -> evaluateSum();
            case 1 -> evaluateProduct();
            case 2 -> evaluateMin();
            case 3 -> evaluateMax();
            case 4 -> evaluateLiteral();
            case 5 -> evaluateGreaterThan();
            case 6 -> evaluateLessThan();
            case 7 -> evaluateEqualTo();
            default -> 0;
        };
    }

    private long evaluateSum() {
        return LongStreamEx.of(parseOp()).sum();
    }

    private long evaluateProduct() {
        return LongStreamEx.of(parseOp()).reduce(1L, Math::multiplyExact);
    }

    private long evaluateMin() {
        return LongStreamEx.of(parseOp()).min().orElseThrow();
    }

    private long evaluateMax() {
        return LongStreamEx.of(parseOp()).max().orElseThrow();
    }

    private long evaluateGreaterThan() {
        List<Long> values = parseOp();
        return values.get(0) > values.get(1) ? 1 : 0;
    }

    private long evaluateLessThan() {
        List<Long> values = parseOp();
        return values.get(0) < values.get(1) ? 1 : 0;
    }

    private long evaluateEqualTo() {
        List<Long> values = parseOp();
        return Objects.equals(values.get(0), values.get(1)) ? 1 : 0;
    }

    private List<Long> parseOp() {
        List<Long> values = new ArrayList<>();
        if (readBits(1) == 0) {
            int length = readBits(15);
            int currentPos = reader.getPosition();
            while (reader.getPosition() < currentPos + length) {
                values.add(evaluate());
            }
        } else {
            int numPackets = readBits(11);
            for (int p = 0; p < numPackets; p++) {
                values.add(evaluate());
            }
        }

        return values;
    }

    private long evaluateLiteral() {
        return evaluateLiteral(0);
    }

    private long evaluateLiteral(long value) {
        boolean end = readBits(1) == 0;
        value = value << 4 | readBits(4);
        return end ? value : evaluateLiteral(value);
    }

    private int readBits(int length) {
        return reader.readBits(length);
    }
}
