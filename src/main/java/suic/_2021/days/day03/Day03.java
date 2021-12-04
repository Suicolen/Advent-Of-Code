package suic._2021.days.day03;

import one.util.streamex.IntStreamEx;
import org.apache.commons.lang3.tuple.Pair;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.IntStream;

public class Day03 implements Puzzle<Long> {

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

        for (int i = 0; i < length; i++) {
            long count = computeCount(input, i);
            gamma.append(count >= 0 ? '1' : '0');
            epsilon.append(count < 0 ? '1' : '0');
        }

        return Long.parseLong(gamma.toString(), 2) * Long.parseLong(epsilon.toString(), 2);
    }

    public Long solvePart2() {
        int length = input.get(0).length();
        List<String> oxygen = new ArrayList<>(input);
        List<String> co2 = new ArrayList<>(input);

        for (int i = 0; i < length; i++) {
            if (oxygen.size() == 1 && co2.size() == 1) {
                break;
            }

            if (oxygen.size() != 1) {
                filter(oxygen, i, true);
            }

            if (co2.size() != 1) {
                filter(co2, i, false);
            }
        }

        return Long.parseLong(oxygen.get(0), 2) * Long.parseLong(co2.get(0), 2);
    }



    private void filter(List<String> lines, int index, boolean oxygen) {
        long count = computeCount(lines, index);
        if (oxygen) { // if else cuz cba with nested ternary
            lines.removeIf(l -> l.charAt(index) == (count < 0 ? '1' : '0'));
        } else {
            lines.removeIf(l -> l.charAt(index) == (count < 0 ? '0' : '1'));
        }

    }

    private long computeCount(List<String> lines, int index) {
        return lines.stream().mapToLong(l -> l.charAt(index) == '1' ? 1 : -1).sum();
    }
}
