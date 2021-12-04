package suic._2021.days.day02;

import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;

public class Day02 implements Puzzle<Long> {

    private List<String> input;
    private long horizontal = 0;
    private long depth = 0;
    private long aim = 0;

    @Override
    public void init() {
        parse();
        solve();
    }

    @Override
    public void parse() {
        input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }


    public Long solvePart1() {
        return aim * horizontal;
    }

    public Long solvePart2() {
        return depth * horizontal;
    }

    public void solve() {
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
    }

}
