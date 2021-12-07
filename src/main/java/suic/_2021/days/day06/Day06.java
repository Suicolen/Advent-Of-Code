package suic._2021.days.day06;

import one.util.streamex.LongStreamEx;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.DiagonalMatrix;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.Arrays;

public class Day06 implements Puzzle<Long> {


    @Override
    public void init() {
        parse();
    }

    private final long[] fish = new long[9];

    @Override
    public void parse() {
        String input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .get(0);
        for (String s : input.split(",")) {
            int age = Integer.parseInt(s);
            fish[age]++;
        }
    }


    public Long solvePart1() {
        long start = System.nanoTime();
        for (int i = 0; i < 80; i++) {
            age();
        }
        System.out.println("Took " + (System.nanoTime() - start) + "ns");
        return LongStreamEx.of(fish).sum();
    }

    public Long solvePart2() {
        long start = System.nanoTime();
        for (int i = 80; i < 256; i++) { // this will work because we'll always call part 1 first
            age();
        }
        System.out.println("Took " + (System.nanoTime() - start) + "ns");
        return LongStreamEx.of(fish).sum();
    }

    private void age() {
        long cycle = fish[0];
        System.arraycopy(fish, 1, fish, 0, 8);
        fish[8] = cycle;
        fish[6] += cycle;
    }

}
