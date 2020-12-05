package suic.days.day5;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 implements Puzzle<Integer, Integer> {

    @Override
    public List<Integer> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(line -> line.replace("F", "0")
                        .
                                replace("B", "1")
                        .
                                replace("L", "0")
                        .
                                replace("R", "1"))
                .mapToInt(b -> Integer.parseInt(b, 2))
                .boxed()
                .collect(Collectors.toList());
    }


    @Override
    public Integer solvePart1() {
        return parse().stream()
                .max(Comparator.naturalOrder())
                .orElse(-1);
    }

    @Override
    public Integer solvePart2() {
        Set<Integer> seatIds = new HashSet<>(parse());
        int start = IntStream.range(1, seatIds.size() - 1)
                .filter(seatIds::contains)
                .findFirst()
                .orElse(seatIds.size());

        return IntStream.range(start, start + seatIds.size())
                .filter(i -> !seatIds.contains(i))
                .findFirst()
                .orElse(-1);
    }
}
