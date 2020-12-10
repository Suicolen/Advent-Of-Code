package suic.days.day05;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 implements Puzzle<Integer, Integer> {


    final Pattern FL_PATTERN = Pattern.compile("[FL]");
    final Pattern BR_PATTERN = Pattern.compile("[BR]");

    @Override
    public List<Integer> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(line -> FL_PATTERN.matcher(line)
                        .replaceAll("0"))
                .map(line -> BR_PATTERN.matcher(line)
                        .replaceAll("1"))
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
