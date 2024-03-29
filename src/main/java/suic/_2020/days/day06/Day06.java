package suic._2020.days.day06;

import suic._2020.Puzzle;
import suic.util.FileUtils;
import java.util.*;

public class Day06 implements Puzzle<String, Long> {

    @Override
    public List<String> parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        List<String> parsedInput = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String line : input) {
            if (line.isEmpty()) {
                parsedInput.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(line).append("\n");
            }
        }
        if (!sb.isEmpty()) {
            parsedInput.add(sb.toString());
        }

       // Function<List<String>, String> f = null;
       // List<String> result = input.stream().collect(Collectors.collectingAndThen());
      //  System.out.println(result.size() + " vs " + parsedInput.size());
        return parsedInput;
    }

    @Override
    public Long solvePart1() {
        return parse().stream().mapToLong(this::countFirst).sum();
    }

    @Override
    public Long solvePart2() {
        return parse().stream().mapToLong(this::countSecond).sum();
    }

    private long countFirst(String group) {
        return group.chars().filter(c -> c != '\n').distinct().count();
    }

    private long countSecond(String group) {
        List<String> passengers = Arrays.asList(group.split("\n"));
        return passengers.get(0).chars().filter(c -> passengers.stream()
                .filter(passenger -> passenger.indexOf(c) >= 0).count() == passengers.size())
                .count();
    }
}