package suic._2021.days.day20;

import suic._2021.Puzzle;
import suic.util.FileUtils;
import suic.util.StringUtils;

import java.util.BitSet;
import java.util.List;

public class Day20 implements Puzzle<Integer> {

    private BitSet algorithm;
    private boolean[][] image;

    @Override
    public void parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        String first = input.get(0);
        algorithm = StringUtils.toBitSet(first);
        image = input.stream().skip(2).map(StringUtils::toBooleanArray).toArray(boolean[][]::new);
    }

    @Override
    public Integer solvePart1() {
        boolean[][] first = transform(image, false);
        boolean[][] second = transform(first, algorithm.get(0));
        return countLit(second);
    }

    @Override
    public Integer solvePart2() {
        boolean[][] grid = image;
        boolean surrounding = false;
        boolean reverse = algorithm.get(0) && !algorithm.get(511);
        for (int i = 0; i < 50; i++) {
            grid = transform(grid, surrounding);
            if (reverse) {
                surrounding = !surrounding;
            }
        }
        return countLit(grid);
    }

    private boolean[][] transform(boolean[][] grid, boolean surrounding) {
        boolean[][] result = new boolean[grid.length + 2][grid[0].length + 2];
        int idx = 0;
        for (int x = -1; x <= grid.length; x++) {
            boolean[] row = new boolean[grid.length + 2];
            int i = 0;
            for (int y = -1; y <= grid.length; y++) {
                row[i++] = algorithm.get(getTrio(grid, x, y, surrounding));
            }
            result[idx++] = row;
        }
        return result;
    }

    private boolean get(boolean[][] grid, int x, int y, boolean surrounding) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return surrounding;
        }

        return grid[x][y];
    }

    private int getTrio(boolean[][] grid, int x, int y, boolean surrounding) {
        int result = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                result <<= 1;
                if (get(grid, x + dx, y + dy, surrounding)) {
                    result++;
                }
            }
        }
        return result;
    }

    private int countLit(boolean[][] image) {
        int result = 0;
        for (boolean[] outer : image) {
            for (boolean inner : outer) {
                if (inner) {
                    result++;
                }
            }
        }
        return result;
    }
}
