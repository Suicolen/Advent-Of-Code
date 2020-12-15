package suic.days.day12;

import lombok.*;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.List;
import java.util.stream.Collectors;

// :( gotta refactor
public class Day12 implements Puzzle<Action, Integer> {
    @Override
    public List<Action> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt")
                .stream()
                .map(line -> new Action(line.charAt(0), Integer.parseInt(line.substring(1))))
                .collect(Collectors.toList());
    }

    @Override
    public Integer solvePart1() {
        List<Action> actions = parse();
        Point point = new Point(0, 0);
        Direction direction = Direction.EAST;
        for (Action action : actions) {
            char dir = action.getDir();
            int value = action.getValue();
            switch (dir) {
                case 'N' -> point.y += value;
                case 'E' -> point.x += value;
                case 'S' -> point.y -= value;
                case 'W' -> point.x -= value;
                case 'F' -> {
                    point.x += direction.getOffset().x * value;
                    point.y += direction.getOffset().y * value;
                }
                case 'L' -> direction = Direction.rotate(direction, value / 90, false);
                case 'R' -> direction = Direction.rotate(direction, value / 90, true);
            }

        }
        return Math.abs(point.x) + Math.abs(point.y);
    }

    @Override
    public Integer solvePart2() {
        List<Action> actions = parse();
        Point sp = new Point(0, 0);
        Point wp = new Point(10, 1);

        for (Action action : actions) {
            char dir = action.getDir();
            int value = action.getValue();
            switch (dir) {
                case 'N' -> wp.y += value;
                case 'E' -> wp.x += value;
                case 'S' -> wp.y -= value;
                case 'W' -> wp.x -= value;
                case 'F' -> {
                    sp.x += wp.x * value;
                    sp.y += wp.y * value;
                }
                case 'L' -> {
                    for (int i = 0; i < value / 90; i++) {
                        int temp = wp.y;
                        //noinspection SuspiciousNameCombination
                        wp.y = wp.x;
                        wp.x = temp * -1;
                    }
                }
                case 'R' -> {
                    for (int i = 0; i < value / 90; i++) {
                        int temp = wp.y;
                        wp.y = wp.x * -1;
                        wp.x = temp;
                    }
                }
            }

        }
        return Math.abs(sp.x) + Math.abs(sp.y);
    }
}
