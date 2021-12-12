package suic._2021.days.day12;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;

import static java.util.function.Predicate.not;

public class Day12 implements Puzzle<Long> {

    private final Map<String, List<String>> graph = new HashMap<>(); // slower alternative(in this case) would be a MultiMap<String, String>
    private final String START = "start";
    private final String END = "end";

    @Override
    public void init() {
        parse();

        for (int i = 1; i <= 100; i++) {
            long start = System.nanoTime();
            solvePart2();
            System.out.println("Took " + (System.nanoTime() - start) / 1e+6 + "ms");
        }

    }

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        for (String line : lines) {
            String[] sp = line.split("-");
            graph.computeIfAbsent(sp[0], k -> new ArrayList<>()).add(sp[1]);
            graph.computeIfAbsent(sp[1], k -> new ArrayList<>()).add(sp[0]);
        }
    }

    private long solve(boolean part2) {
        Deque<Node> nodes = new ArrayDeque<>();
        nodes.push(new Node(START, "", null)); // current pos, path, small cave
        int paths = 0;
        while (!nodes.isEmpty()) {
            Node currentPath = nodes.pop();

            for (String current : graph.get(currentPath.currentPos)) {
                if (current.equals(END)) { // visited end
                    paths++;
                    continue;
                }

                if (current.equals(START)) { // ignore the start node
                    continue;
                }

                String path = currentPath.path;
                String smallCave = currentPath.smallCave;
                if (current.toLowerCase().equals(current)) {// small cave
                    if (!path.contains(current)) { // not visited yet
                        nodes.add(new Node(current, path + current, smallCave));
                    } else if (part2 && smallCave == null) {
                        nodes.add(new Node(current, path + current, current));
                    }
                } else {
                    nodes.add(new Node(current, path + current, smallCave));
                }
            }
        }
        return paths;
    }

    private record Node(String currentPos, String path, String smallCave) {}

    @Override
    public Long solvePart1() {
        return solve(false);
    }

    @Override
    public Long solvePart2() {
        return solve(true);
    }
}
