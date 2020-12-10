package suic.days.day10;

import one.util.streamex.LongStreamEx;
import one.util.streamex.MoreCollectors;
import one.util.streamex.StreamEx;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        List<Long> input = parse().stream().sorted().collect(Collectors.toList());
        long[] count = new long[3];
        IntStream.range(1, input.size()).boxed()
                .map(i -> (int) (input.get(i) - input.get(i - 1) - 1))
                .forEach(i -> count[i]++);
        Map<Long, List<Long>> stepCounts = StreamEx.ofSubLists(input, 2)
                .mapToLong(sublist -> sublist.get(1) - sublist.get(0)).mapToEntry(x -> x, x -> (long) Collections.frequency(input,x)).grouping();
        System.out.println(stepCounts.get(1L).size() + " | " + stepCounts.get(3L).size());
        long result = stepCounts.get(1L).size() * stepCounts.get(3L).size();
        System.out.println("Result = " + result);
        System.out.println(Arrays.toString(count));
        return (count[0] + 1) * (count[2] + 1);
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
