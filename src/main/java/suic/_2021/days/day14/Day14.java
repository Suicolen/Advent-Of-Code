package suic._2021.days.day14;

import one.util.streamex.IntStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day14 implements Puzzle<Long> {

    @Override
    public void init() {
        parse();
    }

    private String polymer;
    private Map<String, String> insertions;
    private final int ALPHABET_COUNT = 26;

    @Override
    public void parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        polymer = input.get(0);
        insertions = input.stream()
                .skip(2)
                .map(s -> s.split(" -> "))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    public Long solvePart1() {
        return solve(10);
    }

    public Long solvePart2() {
        return solve(40);
    }

    private long solve(int steps) {
        Map<String, Long> counter = createPairCounts();
        for (int step = 0; step < steps; step++) {
            counter = step(counter);
        }
        return computeResult(counter);
    }

    private Map<String, Long> createPairCounts() {
        return IntStreamEx.range(polymer.length() - 1)
                .boxed()
                .collect(Collectors.toMap(i -> polymer.substring(i, i + 2), i -> 1L, Long::sum, HashMap::new));
    }

    private Map<String, Long> step(Map<String, Long> pairCounts) {
        Map<String, Long> newPairCounts = new HashMap<>();
        pairCounts.forEach((pair, count) -> {
            String insertion = insertions.get(pair);
            if (insertion != null) {
                newPairCounts.merge(pair.charAt(0) + insertion, count, Long::sum);
                newPairCounts.merge(insertion + pair.charAt(1), count, Long::sum);
            }
        });
        return newPairCounts;
    }

    private long computeResult(Map<String, Long> pairCounts) {
        long[] counts = new long[ALPHABET_COUNT];
        pairCounts.forEach((pair, count) -> counts[pair.charAt(0) - 'A'] += count);
        counts[polymer.charAt(polymer.length() - 1) - 'A']++;
        LongSummaryStatistics stats = LongStream.of(counts).filter(x -> x > 0).summaryStatistics();
        return stats.getMax() - stats.getMin();
    }
}
