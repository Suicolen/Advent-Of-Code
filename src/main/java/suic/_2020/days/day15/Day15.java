package suic._2020.days.day15;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 implements Puzzle<Integer, Integer> {
    @Override
    public List<Integer> parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        return Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    public Integer solvePart1() {
        return solve(2020);
    }

    @Override
    public Integer solvePart2() {
        return solve(30000000);
    }

    private int solve(int target) {
        List<Integer> input = parse();
        Map<Integer, Integer> indices = IntStream.range(0, input.size())
                .boxed()
                .collect(Collectors.toMap(input::get, i -> i));
        while (input.size() < target) {
            int prevNum = input.get(input.size() - 1);
            int prevIndex = indices.getOrDefault(prevNum, -1);
            indices.put(prevNum, input.size() - 1);
            int next = prevIndex == -1 ? 0 : input.size() - 1 - prevIndex;
            input.add(next);
        }
        return input.get(input.size() - 1);
    }
}