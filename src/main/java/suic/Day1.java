package suic;

import suic.util.FileUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 implements Puzzle<Integer> {
    public static void main(String[] args) {
        List<Integer> input = FileUtils.readResource("Day1Input.txt").stream().map(Integer::parseInt).collect(Collectors
                .toList());
        Day1 day1 = new Day1();
        int part1Result = day1.solvePart1(input);
        System.out.println("Part 1 result = " + part1Result);
        int part2Result = day1.solvePart2(input);
        System.out.println("Part 2 result = " + part2Result);
    }

    @Override
    public Integer solvePart1(List<Integer> input) {
        int target = 2020;
        Set<Integer> occurred = new HashSet<>();
        for (int value : input) {
            occurred.add(value);
            if (occurred.contains(target - value)) {
                return value * (target - value);
            }
        }
        return -1;
    }

    @Override
    public Integer solvePart2(List<Integer> input) {
        int target = 2020;
        Collections.sort(input);
        for (int i = 0; i < input.size() - 2; i++) {
            if (i == 0 || !input.get(i).equals(input.get(i - 1))) {
                int low = i + 1;
                int high = input.size() - 1;
                int sum = target - input.get(i);
                while (low < high) {
                    if (input.get(low) + input.get(high) == sum) {
                        return input.get(i) * input.get(low) * input.get(high);
                    } else if (input.get(low) + input.get(high) > sum) {
                        high--;
                    } else {
                        low++;
                    }
                }
            }
        }
        return -1;
    }
}
