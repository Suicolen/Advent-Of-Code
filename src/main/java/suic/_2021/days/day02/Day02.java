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
        return solve(false);
    }

    public Long solvePart2() {
        return solve(true);
    }

    //java please don't hate me for skipping braces :(
    public Long solve(boolean aiming) {
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
                    if (aiming) depth += value * aim;
                }
                case "down" -> {
                    if(aiming) aim += value; else depth += value;
                }
                case "up" -> {
                    if(aiming) aim -= value; else depth -= value;
                }
            }
        }
        return horizontal * depth;
    }

}
