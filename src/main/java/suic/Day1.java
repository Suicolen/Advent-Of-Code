package suic;

import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) {
        List<Integer> input = FileUtils.readResource("Day1Input.txt").stream().map(Integer::parseInt).collect(Collectors
                .toList());
        int target = 2020;
        int part1Result = solvePart1(input, target);
        System.out.println("Part 1 result = " + part1Result);
        int part2Result = solvePart2(input, target);
        System.out.println("Part 2 result = " + part2Result);
    }

    private static int solvePart1(List<Integer> input, int target) {
        Map<Integer, Integer> occured = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            if (occured.containsKey(target - input.get(i))) {
                return (target - input.get(i)) * input.get(i);
            } else {
                occured.put(input.get(i), i);
            }
        }
        return -1;
    }

    private static int solvePart2(List<Integer> input, int target) {
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
