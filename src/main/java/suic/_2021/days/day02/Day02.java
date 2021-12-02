package suic._2021.days.day02;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;

public class Day02 implements Puzzle<String, Long> {

    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }


    public Long solvePart1() {
        List<String> input = parse();
        long horizontal = 0;
        long depth = 0;
        for (String str : input) {
            String[] data = str.split(" ");
            int value = Integer.parseInt(data[1]);
            switch (data[0]) {
                case "forward" -> horizontal += value;
                case "down" -> depth += value;
                case "up" -> depth -= value;
            }
        }
        return horizontal * depth;
    }

    public Long solvePart2() {
        List<String> input = parse();
        long horizontal = 0;
        long depth = 0;
        long aim = 0;
        for (String str : input) {
            String[] data = str.split(" ");
            int value = Integer.parseInt(data[1]);
            switch (data[0]) {
                case "forward" -> {
                    horizontal += value;
                    depth += value * aim;
                }
                case "down" -> aim += value;
                case "up" -> aim -= value;
            }
        }
        return horizontal * depth;
    }

}
