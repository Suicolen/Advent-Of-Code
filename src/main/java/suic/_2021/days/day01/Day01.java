package suic._2021.days.day01;

import one.util.streamex.IntStreamEx;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 implements Puzzle<Long, Long> {

    @Override
    public List<Long> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(Long::parseLong)
                .collect(Collectors
                        .toList());
    }


    public Long solvePart1() {
        List<Long> input = parse();
        return LongStreamEx.of(input).pairMap((a, b) -> b > a ? 1 : 0).sum();
    }

    public Long solvePart2() {
        List<Long> input = parse();
        return IntStream.range(0, input.size() - 3).filter(i -> input.get(i) < input.get(i + 3)).count();
    }
}
