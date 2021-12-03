package suic._2021.days.day03;

import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.IntStream;

public class Day03 implements Puzzle<String, Long> {

    private List<String> input;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }


    public Long solvePart1() {
        int length = input.get(0).length();
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            long count = computeCount(input, i);
            if (count < 0) {
                gamma.append('0');
                epsilon.append('1');
            } else {
                gamma.append('1');
                epsilon.append('0');
            }
        });
        return Long.parseLong(gamma.toString(), 2) * Long.parseLong(epsilon.toString(), 2);
    }

    public Long solvePart2() {
        int length = input.get(0).length();
        List<String> oxygen = new ArrayList<>(input);
        List<String> co2 = new ArrayList<>(input);
        IntStream.range(0, length).takeWhile(i -> oxygen.size() != 1).forEach(i -> {
            long count = computeCount(oxygen, i);
            oxygen.removeIf(l -> l.charAt(i) == (count < 0 ? '1' : '0'));
        });

        IntStream.range(0, length).takeWhile(i -> co2.size() != 1).forEach(i -> {
            long count = computeCount(co2, i);
            co2.removeIf(l -> l.charAt(i) == (count < 0 ? '0' : '1'));
        });

        return Long.parseLong(oxygen.get(0), 2) * Long.parseLong(co2.get(0), 2);
    }

    private long computeCount(List<String> lines, int index) {
        return lines.stream().mapToLong(l -> l.charAt(index) == '1' ? 1 : -1).sum();
    }
}
