package suic._2021.days.day01;

import one.util.streamex.IntStreamEx;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 implements Puzzle<Integer, Long> {

    @Override
    public List<Integer> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors
                        .toList());
    }

    public Long solvePart1() {
        List<Integer> input = parse();
        return IntStreamEx.of(input).pairMap((a, b) -> a > b ? 1 : 0).filter(i -> i == 0).count();
    }

    public Long solvePart2() {
        List<Integer> input = parse();
        return IntStream.range(0, input.size() - 3).filter(i -> {
            int currentDepth = input.get(i) + input.get(i + 1) + input.get(i + 2);
            int nextDepth = input.get(i + 1) + input.get(i + 2) + input.get(i + 3);
            return nextDepth > currentDepth;
        }).count();

    }

}
