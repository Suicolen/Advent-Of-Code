package suic._2021.days.day17;

import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day17 implements Puzzle<Integer> {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    private final Pattern AREA_REGEX = Pattern.compile("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)");

    @Override
    public void parse() {
        String input = FileUtils.readResourceAsString(getClass().getSimpleName() + "Input.txt");
        Matcher matcher = AREA_REGEX.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Regex didn't match");
        }
        minX = parseInt(matcher.group(1));
        maxX = parseInt(matcher.group(2));
        minY = parseInt(matcher.group(3));
        maxY = parseInt(matcher.group(4));
    }

    @Override
    public Integer solvePart1() {
        int initialVel = -minY - 1;
        return (initialVel + 1) * initialVel / 2;
    }

    @Override
    public Integer solvePart2() {
        int hitTarget = 0;
        for (int y = minY; y < 500; y++) {
            for (int x = 1; x < maxX + 1; x++) {
                int height = shoot(x, y, minX, maxX, minY, maxY);
                if (height > Integer.MIN_VALUE) {
                    hitTarget++;
                }
            }
        }
        return hitTarget;
    }

    private int shoot(int xVel, int yVel, int targetMinX, int targetMaxX, int targetMinY, int targetMaxY) {
        int maxY = Integer.MIN_VALUE;
        int x = 0;
        int y = 0;
        while (y >= targetMinY) {
            x += xVel;
            y += yVel;

            if (y > maxY) {
                maxY = y;
            }

            if (targetMinX <= x && targetMaxX >= x && targetMinY <= y && targetMaxY >= y) {
                return maxY;
            }

            xVel += Integer.compare(0, xVel);
            yVel--;
        }
        return Integer.MIN_VALUE;
    }
}
