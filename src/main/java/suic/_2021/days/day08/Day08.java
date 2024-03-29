package suic._2021.days.day08;

import lombok.val;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day08 implements Puzzle<Long> {

    private List<Data> input;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        input = FileUtils.readResourceAsStream(getClass().getSimpleName() + "Input.txt").map(line -> {
            String[] data = line.split(" \\| ");
            return new Data(data[0].split(" "), data[1].split(" "));
        }).toList();
    }


    public Long solvePart1() {
        List<Integer> uniqueDigits = List.of(2, 3, 4, 7); // list is faster in this case(compared to a set)
        return input.stream().mapToLong(data -> {
            long count = 0;
            for (String str : data.output) {
                if (uniqueDigits.contains(str.length())) {
                    count++;
                }
            }
            return count;
        }).sum();
    }

    public Long solvePart2() {
        long sum = 0;
        String[] digits = new String[10];


        for (Data data : input) {
            String[] patterns = data.patterns;
            String[] output = data.output;
            getKnownValues(digits, patterns);
            findOfLengthSix(digits, patterns);
            findOfLengthFive(digits, patterns);

            for (int i = 0; i < 10; i++) {
                digits[i] = sort(digits[i]);
            }

            sum += compute(output, digits);
        }
        return sum;
    }

    private int compute(String[] output, String[] sortedDigits) {
        int number = 0;
        for (String s : output) {
            String sorted = sort(s);
            for (int i = 0; i < 10; i++) {
                if (sortedDigits[i].equals(sorted)) {
                    number = (number * 10) + i;
                }
            }
        }
        return number;
    }

    private void findOfLengthSix(String[] digits, String[] patterns) {
        for (String pattern : patterns) {
            if (pattern.length() == 6) {
                if (isNine(digits, pattern)) {
                    digits[9] = pattern;
                } else if (isZeroOrThree(digits, pattern)) {
                    digits[0] = pattern;
                } else {
                    digits[6] = pattern;
                }
            }
        }
    }

    private void findOfLengthFive(String[] digits, String[] patterns) {
        for (String pattern : patterns) {
            if (pattern.length() == 5) {
                if (isZeroOrThree(digits, pattern)) {
                    digits[3] = pattern;
                } else if (isFive(digits, pattern)) {
                    digits[5] = pattern;
                } else {
                    digits[2] = pattern;
                }
            }
        }
    }

    private void getKnownValues(String[] digits, String[] patterns) {
        for (String pattern : patterns) {
            switch (pattern.length()) {
                case 2 -> digits[1] = pattern;
                case 3 -> digits[7] = pattern;
                case 4 -> digits[4] = pattern;
                case 7 -> digits[8] = pattern;
            }
        }
    }

    private String sort(String digits) {
        return IntStreamEx.ofChars(digits).sorted().charsToString();
    }

    private boolean isNine(String[] digits, String patterns) {
        return toCharStream(digits[4]).allMatch(c -> patterns.contains(c.toString()));
    }

    private boolean isZeroOrThree(String[] digits, String patterns) {
        return toCharStream(digits[1]).allMatch(c -> patterns.contains(c.toString()));
    }

    private boolean isFive(String[] digits, String patterns) {
        return toCharStream(digits[6]).filter(c -> !patterns.contains(c.toString())).count() == 1;
    }

    private Stream<Character> toCharStream(String str) {
        return str.chars().mapToObj(c -> (char) c);
    }

    private record Data(String[] patterns, String[] output) {} // dunno what else to call it

}
