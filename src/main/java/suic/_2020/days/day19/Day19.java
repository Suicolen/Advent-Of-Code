package suic._2020.days.day19;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 implements Puzzle<String, Integer> {
    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    private Map<Integer, List<List<Integer>>> rules;
    private Map<Integer, Character> rulesEnd;

    @Override
    public Integer solvePart1() {
        List<String> input = parse();

        rules = new HashMap<>();
        rulesEnd = new HashMap<>();
        boolean firstStage = true;
        int count = 0;
        for (String line : input) {
            if (firstStage) {
                if (line.isEmpty()) {
                    firstStage = false;
                    continue;
                }
                String[] parts = line.split(": ");
                Integer ruleId = Integer.parseInt(parts[0]);
                List<List<Integer>> rulesList = new ArrayList<>();
                if (parts[1].contains("\"")) {
                    rulesEnd.put(ruleId, parts[1].replace("\"", "").charAt(0));
                } else {
                    for (String expressions : parts[1].split(" \\| ")) {
                        List<Integer> list = new ArrayList<>();
                        for (String expRule : expressions.trim().split(" ")) {
                            list.add(Integer.parseInt(expRule));
                        }
                        rulesList.add(list);
                    }
                    rules.put(ruleId, rulesList);
                }
            } else {
                
            }

        }

        return count;
    }

    @Override
    public Integer solvePart2() {

        return null;
    }
}
