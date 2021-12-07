package suic._2021.days.day07;

import one.util.streamex.IntStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class Day07 implements Puzzle<Long> {

    List<Integer> positions = new ArrayList<>();

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        String input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .get(0);
        for (String s : input.split(",")) {
            int pos = Integer.parseInt(s);
            positions.add(pos);
        }
    }


    public Long solvePart1() {
        return solve(false);
    }

    public Long solvePart2() {
        return solve(true);
    }

    public Long solve(boolean part2) {
        long start = System.nanoTime();
        int low = IntStreamEx.of(positions).min().orElseThrow();
        int high = IntStreamEx.of(positions).max().orElseThrow();
        long best = Long.MAX_VALUE;

        for (int current = low; current <= high; current++) {
            long target = 0L;

            for (int pos : positions) {
                long dist = Math.abs(pos - current);
                target += part2 ? (dist * (dist + 1)) / 2 : dist;
            }

            if (best > target) {
                best = target;
            }
        }
        System.out.println("Took " + (System.nanoTime() - start) + "ns to compute " + "Part " + (part2 ? "2" : 1));
        return best;
    }


}

