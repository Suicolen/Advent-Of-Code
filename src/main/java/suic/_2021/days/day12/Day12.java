package suic._2021.days.day12;

import com.google.common.collect.*;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;

import static java.util.function.Predicate.not;

public class Day12 implements Puzzle<Long> {

    private Multimap<String, String> graph;
    private final String START = "start";
    private final String END = "end";

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        graph = MultimapBuilder.hashKeys().hashSetValues().build();
        for (String line : lines) {
            String[] sp = line.split("-");
            // this is equivalent to using a Map<String, Set<String>> and doing:
            //graph.computeIfAbsent(sp[0], k -> new HashSet<>()).add(sp[1]);
            //graph.computeIfAbsent(sp[1], k -> new HashSet<>()).add(sp[0]);
            graph.put(sp[0], sp[1]);
            graph.put(sp[1], sp[0]);
        }
    }

    protected long solve(boolean part2) {
        Deque<Node> queue = new ArrayDeque<>();
        Set<Node> validPaths = new HashSet<>();

        queue.add(new Node(List.of(START), false));

        while (!queue.isEmpty()) {
            Node node = queue.removeLast();
            String current = node.paths.get(node.paths.size() - 1);

            if (END.equals(current)) {
                validPaths.add(node);
                continue;
            }

            Collection<String> currentPaths = graph.get(current);
            // using a list would be faster in this case(due to small number of elements) but using a set is still the right way
            Set<String> possible = new HashSet<>();
            Set<String> secondSmallPossibles = node.secondSmall ? null : new HashSet<>();

            for (String cave : currentPaths) {
                if (START.equals(cave)) {
                    continue;
                }

                if (node.paths.contains(cave) && cave.toLowerCase().equals(cave)) {
                    if (secondSmallPossibles != null) {
                        secondSmallPossibles.add(cave);
                    }
                    continue;
                }
                possible.add(cave);
            }

            if (secondSmallPossibles != null) {
                for (String cave : secondSmallPossibles) {
                    List<String> newPath = copyWith(node.paths, cave);
                    queue.add(new Node(newPath, true));
                }
            }

            for (String cave : possible) {
                List<String> newPath = copyWith(node.paths, cave);
                queue.add(new Node(newPath, node.secondSmall));
            }
        }

        return part2 ? validPaths.size() : computePart1Count(validPaths);
    }

    @Override
    public Long solvePart1() {
        return solve(false);
    }

    @Override
    public Long solvePart2() {
        return solve(true);
    }

    private long computePart1Count(Set<Node> validPaths) {
        return validPaths.stream().filter(not(Node::secondSmall)).count();
    }

    private List<String> copyWith(List<String> original, String element) {
        List<String> copy = new ArrayList<>(original);
        copy.add(element);
        return copy;
    }

    private record Node(List<String> paths, boolean secondSmall) {
    }
}
