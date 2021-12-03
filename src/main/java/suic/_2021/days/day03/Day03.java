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
            long zeros = input.stream().filter(l -> l.charAt(i) == '0').count();
            long ones = input.stream().filter(l -> l.charAt(i) == '1').count();

            if (zeros > ones) {
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
            long count = oxygen.stream().mapToLong(l -> l.charAt(i) == '1' ? 1 : -1).sum();
            oxygen.removeIf(l -> l.charAt(i) == (count < 0 ? '1' : '0'));
        });

        IntStream.range(0, length).takeWhile(i -> co2.size() != 1).forEach(i -> {
            long count = co2.stream().mapToLong(l -> l.charAt(i) == '1' ? 1 : -1).sum();
            co2.removeIf(l -> l.charAt(i) == (count < 0 ? '0' : '1'));
        });

        return Long.parseLong(oxygen.get(0), 2) * Long.parseLong(co2.get(0), 2);
    }
}
