package suic.days.day9;

import com.google.common.base.Functions;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day9 implements Puzzle<Long, Long> {

    @Override
    public List<Long> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    @Override
    public Long solvePart1() {
        List<Long> input = parse();
        return part1Sum(input);
    }


    @Override
    public Long solvePart2() {
        long target = solvePart1();
        List<Long> input = parse();
        return part2Sum(input, target);
    }

    private long part1Sum(List<Long> input) {
        int index = 0;
        while (true) {
            List<Long> preceding25 = input.stream()
                    .skip(index)
                    .limit(25)
                    .collect(Collectors.toList());
            long target = input.get(index + 25);
            long result = twoSum(preceding25, target);
            if (result != target) {
                return target;
            }
            index++;
        }
    }

    private long twoSum(List<Long> numbers, long target) {
        Set<Long> occurred = new HashSet<>();
        for (long value : numbers) {
            occurred.add(value);
            if (occurred.contains(target - value)) {
                return value + (target - value);
            }
        }
        return -1L;
    }

    private long part2Sum(List<Long> numbers, long target) {
        int i = 0;
        int j = 1;
        long sum = numbers.get(0) + numbers.get(1);
        while (sum != target) {
            if (sum < target) {
                j++;
                sum += numbers.get(j);
            }
            if (sum > target) {
                sum -= numbers.get(i);
                i++;
            }
        }
        List<Long> range = numbers.subList(i, j + 1);
        LongSummaryStatistics statistics = range.stream().mapToLong(Long::longValue).summaryStatistics();
        return statistics.getMin() + statistics.getMax();
    }
}
