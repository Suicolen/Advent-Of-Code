package suic._2021.days.day11;

import one.util.streamex.IntStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;
import suic.util.GridHelper;

import java.util.List;

public class Day11 implements Puzzle<Integer> {

    private int[][] octopuses;
    private int maxX;
    private int maxY;
    private int size;

    private int firstFullFlash = -1;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        octopuses = GridHelper.parseAsInt(lines);
        //they're the same in this case, but this is more clear
        maxX = octopuses.length;
        maxY = octopuses[0].length;
        size = maxX * maxY;

        int warmups = 1000;
        long[] times = new long[warmups];
        for(int i = 0; i < warmups; i++) {
            long start = System.nanoTime();
            solvePart1();
            solvePart2();
            times[i] = System.nanoTime() - start;
        }

        System.out.println("Took " + times[warmups - 1] + "ns");
    }

    private int step(int step) {
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                tryFlash(x, y);
            }
        }

        int flashes = 0;

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (octopuses[x][y] == 10) {
                    flashes++;
                    octopuses[x][y] = 0;
                }
            }
        }

        if (firstFullFlash == -1 && flashes == size) {
            firstFullFlash = step;
        }

        return flashes;
    }

    private void tryFlash(int x, int y) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }

        if (octopuses[x][y] < 9) {
            octopuses[x][y]++;
        } else if (octopuses[x][y] == 9) {
            octopuses[x][y] = 10;
            for (int dx = x - 1; dx <= x + 1; dx++) {
                for (int dy = y - 1; dy <= y + 1; dy++) {
                    tryFlash(dx, dy);
                }
            }
        }
    }

    public Integer solvePart1() {
        return IntStreamEx.range(size).map(this::step).reduce(0, Integer::sum);
    }

    public Integer solvePart2() {
        int step = size + 1;
        while (firstFullFlash == -1) {
            step(step++);
        }
        return firstFullFlash;
    }
}
