package suic._2021.days.day08;

import one.util.streamex.IntStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Stream;

public class Day08 implements Puzzle<Long> {

    private List<Data> input;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");

        input = lines.stream().map(line -> {
            String[] data = line.split(" \\| ");
            return new Data(data[0].split(" "), data[1].split(" "));
        }).toList();
    }


    public Long solvePart1() {
        List<Integer> uniqueDigits = List.of(2, 3, 4, 7);
        return input.stream().mapToLong(line -> {
            long count = 0;
            for (String str : line.output) {
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
            for (String pattern : patterns) {
                switch (pattern.length()) {
                    case 2 -> digits[1] = pattern;
                    case 3 -> digits[7] = pattern;
                    case 4 -> digits[4] = pattern;
                    case 7 -> digits[8] = pattern;
                }
            }
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

            for (int i = 0; i < 10; i++) {
                digits[i] = sort(digits[i]);
            }

            int number = 0;
            for (String s : output) {
                String sorted = sort(s);
                for (int i = 0; i < 10; i++) {
                    if (digits[i].equals(sorted)) {
                        number = (number * 10) + i;
                    }
                }
            }

            sum += number;
        }
        return sum;
    }

    public String sort(String digits) {
        return IntStreamEx.ofChars(digits).sorted().charsToString();
    }

    public boolean isNine(String[] digits, String patterns) {
        return toCharStream(digits[4]).allMatch(c -> patterns.contains(c.toString()));
    }

    public boolean isZeroOrThree(String[] digits, String patterns) {
        return toCharStream(digits[1]).allMatch(c -> patterns.contains(c.toString()));
    }

    public boolean isFive(String[] digits, String patterns) {
        return toCharStream(digits[6]).filter(c -> !patterns.contains(c.toString())).count() == 1;
    }

    private Stream<Character> toCharStream(String str) {
        return str.chars().mapToObj(c -> (char) c);
    }

    private record Data(String[] patterns, String[] output) { // dunno what else to call it
    }


}
