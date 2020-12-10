package suic.days.day07;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day07 implements Puzzle<String, Integer> {

    private static final Pattern MAIN_PATTERN = Pattern.compile("(.+) bags contain (.+)\\.");
    private static final Pattern SUB_PATTERN = Pattern.compile("(\\d+?) (.+?) bags?");
    private final Map<String, BagNode> nodes = new HashMap<>();

    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    public Day07() { // meh
        parseNodes();
    }

    private void parseNodes() {
        List<String> input = parse();
        for (String line : input) {
            Matcher mainMatcher = MAIN_PATTERN.matcher(line);
            if (!mainMatcher.matches()) {
                continue;
            }
            BagNode node = nodes.computeIfAbsent(mainMatcher.group(1), BagNode::new);
            String bags = mainMatcher.group(2);
            if (bags.equals("no other bags")) {
                continue;
            }

            String[] split = bags.split(", ");
            for (String part : split) {
                Matcher subMatcher = SUB_PATTERN.matcher(part);
                if (!subMatcher.matches()) {
                    continue;
                }
                int num = Integer.parseInt(subMatcher.group(1));
                BagNode n = nodes.computeIfAbsent(subMatcher.group(2), BagNode::new);
                node.getChildren().put(n, num);
                n.getParents().add(node);
            }
        }
    }

    @Override
    public Integer solvePart1() {
        return calculatePart1(nodes.get("shiny gold"));
    }

    @Override
    public Integer solvePart2() {
        return calculatePart2(nodes.get("shiny gold"));
    }

    private int calculatePart1(BagNode node) {
        return node.getParents()
                .stream()
                .mapToInt(this::calculatePart1)
                .map(x -> x + 1).sum();
    }

    private int calculatePart2(BagNode node) {
        return node.getChildren()
                .entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * (calculatePart2(entry.getKey()) + 1))
                .sum();
    }

}
