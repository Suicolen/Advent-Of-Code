package suic._2020.days.day01;

import suic._2020.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day01 implements Puzzle<Integer, Integer> {

    @Override
    public List<Integer> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt").stream().map(Integer::parseInt).collect(Collectors
                .toList());
    }

    public Integer solvePart1() {
        int target = 2020;
        List<Integer> input = parse();
        Set<Integer> occurred = new HashSet<>();
        for (int value : input) {
            occurred.add(value);
            if (occurred.contains(target - value)) {
                return value * (target - value);
            }
        }
        return -1;
    }

    public Integer solvePart2() {
        int target = 2020;
        List<Integer> input = parse();
        if(input.size() < 3) {
            return -1;
        }
        Collections.sort(input);
        for (int i = 0; i < input.size(); i++) {
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
        return -1;
    }

}
