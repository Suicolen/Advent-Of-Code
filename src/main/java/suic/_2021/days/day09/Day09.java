package suic._2021.days.day09;

import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day09 implements Puzzle<Integer> {

    private int[][] input;
    private int maxX;
    private int maxY;
    private final int HIGHEST = 9;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        int length = lines.get(0).length();
        input = new int[lines.size()][length];
        for (int x = 0; x < lines.size(); x++) {
            for (int y = 0; y < length; y++) {
                input[x][y] = lines.get(x).charAt(y) - '0';
            }
        }

        maxX = input.length;
        maxY = input[0].length;
    }


    public Integer solvePart1() {
        int sum = 0;
        for (int x = 0; x < maxX; x++)
            for (int y = 0; y < maxY; y++) {
                int cur = input[x][y];
                int left = x > 0 ? input[x - 1][y] : HIGHEST;
                int right = x < maxX - 1 ? input[x + 1][y] : HIGHEST;
                int top = y > 0 ? input[x][y - 1] : HIGHEST;
                int bottom = y < maxY - 1 ? input[x][y + 1] : HIGHEST;
                if (cur < left && cur < right && cur < top && cur < bottom) {
                    sum += cur + 1;
                }
            }
        return sum;
    }

    public Integer solvePart2() {
        List<Integer> basinSizes = new ArrayList<>();

        for (int x = 0; x < maxX; x++)
            for (int y = 0; y < maxY; y++) {
                int basinSize = computeBasinSize(x, y);
                if (basinSize != 0) { // pointless to add
                    basinSizes.add(basinSize);
                }
            }

        return basinSizes.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (x, y) -> x * y);
    }

    private int computeBasinSize(int x, int y) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY || input[x][y] == HIGHEST) {
            return 0;
        }

        input[x][y] = HIGHEST;
        return 1 + computeBasinSize(x - 1, y) + computeBasinSize(x + 1, y) + computeBasinSize(x, y - 1) + computeBasinSize(x, y + 1);
    }


}

