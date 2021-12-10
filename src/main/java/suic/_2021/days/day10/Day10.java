package suic._2021.days.day10;

import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import one.util.streamex.StreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.function.Predicate;

public class Day10 implements Puzzle<Long> {

    private List<String> input;

    @Override
    public void init() {
        parse();
    }

    private final Map<Character, Bracket> scoreTable = Map.of(
            ')', new Bracket('(', 3),
            ']', new Bracket('[', 57),
            '}', new Bracket('{', 1197),
            '>', new Bracket('<', 25137)
    );


    private final Map<Character, Bracket> scoreTableInverted = Map.of(
            '(', new Bracket(')', 1),
            '[', new Bracket(']', 2),
            '{', new Bracket('}', 3),
            '<', new Bracket('>', 4)
    );

    @Override
    public void parse() {
        input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    private final List<Queue<Character>> incompleteLines = new ArrayList<>();

    public Long solvePart1() {
        long score = 0;

        for (String line : input) {
            Queue<Character> stack = Collections.asLifoQueue(new ArrayDeque<>());
            boolean incomplete = true;
            for (char c : line.toCharArray()) {
                Bracket bracket = scoreTable.get(c);
                if (bracket == null) {
                    stack.offer(c);
                } else {
                    if (stack.poll() == bracket.opening) {
                        continue;
                    }

                    score += bracket.score;
                    incomplete = false;
                    break;
                }
            }

            if (incomplete) {
                incompleteLines.add(stack);
            }
        }

        return score;
    }

    public Long solvePart2() {
        return incompleteLines.stream()
                .map(this::getScore)
                .sorted()
                .skip(incompleteLines.size() / 2)
                .findFirst()
                .orElseThrow();
    }

    private long getScore(Queue<Character> line) {
        return line.stream()
                .map(c -> scoreTableInverted.get(c).score)
                .reduce(0L, (acc, i) -> acc * 5 + i);
    }

    private record Bracket(char opening, long score) {
    }
}
