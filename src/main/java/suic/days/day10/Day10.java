package suic.days.day10;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 implements Puzzle<Long, Long> {
    @Override
    public List<Long> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(Long::parseLong)
                .collect(Collectors
                        .toList());
    }

    @Override
    public Long solvePart1() {
        List<Long> input = parse();
        long previous = 0;
        long[] count = new long[3];
        count[2] = 1;
        Collections.sort(input);
        for (long rating : input) {
            int difference = (int) (rating - previous);
            count[difference - 1]++;
            previous = rating;
        }
        return count[0] * count[2];
    }


    private long current = 0;

    @Override
    public Long solvePart2() {
        List<Long> input = parse();
        long max = input.stream().mapToLong(Long::longValue).max().orElse(-1L);
        Map<Long, Long> map = new HashMap<>();
        input.forEach(val -> map.put(val, 0L));
        map.put(0L, 1L);
        current = 0;
        while (current < max) {
            List<Integer> list = Stream.of(1, 2, 3)
                    .filter(i -> map.containsKey(current + i))
                    .collect(Collectors.toList());
            list.forEach(i -> map.put(current + i, map.get(current + i) + map.get(current)));
            current = current + list.get(0);
        }
        return map.get(max);
    }
}
