package suic.days.day5;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 implements Puzzle<Integer, Integer> {

    @Override
    public List<Integer> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt").stream().map(line -> line
                .replace("F", "0").
                        replace("B", "1").
                        replace("L", "0").
                        replace("R", "1")).mapToInt(b -> Integer.parseInt(b, 2)).boxed().collect(Collectors.toList());
    }

    @Override
    public Integer solvePart1() {
        return parse().stream().max(Comparator.naturalOrder()).orElse(-1);
    }

    @Override
    public Integer solvePart2() {
        List<Integer> seatIds = parse().stream().sorted().collect(Collectors.toList());
        int first = seatIds.get(1);
        int last = seatIds.get(seatIds.size() - 1);
        int mySeatId = -1;
        for (int i = first; i < last; i++) {
            if (!seatIds.contains(i)) {
                mySeatId = i;
            }
        }
        return mySeatId;
    }
}
