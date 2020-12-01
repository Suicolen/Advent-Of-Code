package suic;

import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) {
        List<Integer> input = FileUtils.readResource("Day1Input.txt").stream().map(Integer::parseInt).collect(Collectors
                .toList());
        int result = solvePart1(input);
        System.out.println("Result = " + result);
    }

    private static int solvePart1(List<Integer> input) {
        int target = 2020;
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
}
