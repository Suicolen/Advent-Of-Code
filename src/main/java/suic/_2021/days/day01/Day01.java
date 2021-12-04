package suic._2021.days.day01;

import one.util.streamex.LongStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.List;
import java.util.stream.IntStream;

public class Day01 implements Puzzle<Long> {

    private List<Long> input;

    @Override
    public void parse() {
        input = FileUtils.readResourceAsLong(getClass().getSimpleName() + "Input.txt");
    }

    public Long solvePart1() {
        return LongStreamEx.of(input).pairMap((a, b) -> b > a ? 1 : 0).sum();
    }

    public Long solvePart2() {
        return IntStream.range(0, input.size() - 3)
                .filter(i -> input.get(i) < input.get(i + 3))
                .count();
    }
}
