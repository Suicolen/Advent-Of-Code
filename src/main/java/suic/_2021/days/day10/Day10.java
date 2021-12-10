package suic._2021.days.day10;

import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;

public class Day10 implements Puzzle<Long> {

    private List<String> input;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    private final List<Queue<Character>> incompleteLines = new ArrayList<>();

    public Long solvePart1() {

        Map<Character, Data> scoreTable = Map.of(
                ')', new Data('(', 3L),
                ']', new Data('[', 57L),
                '}', new Data('{', 1197L),
                '>', new Data('<', 25137L)
        );

        long score = 0;

        for (String line : input) {
            Queue<Character> stack = Collections.asLifoQueue(new ArrayDeque<>());
            boolean incomplete = true;
            for (char c : line.toCharArray()) {
                Data data = scoreTable.get(c);
                if (data == null) {
                    stack.add(c);
                } else {
                    if (stack.poll() == data.opening) {
                        continue;
                    }

                    score += data.score;
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

        Map<Character, Data> scoreTable = Map.of(
                '(', new Data(')', 1L),
                '[', new Data(']', 2L),
                '{', new Data('}', 3L),
                '<', new Data('>', 4L)
        );

        List<Long> scores = new ArrayList<>();

        for (Queue<Character> line : incompleteLines) {
            long score = 0;
            while (!line.isEmpty()) {
                char c = line.poll();
                Data data = scoreTable.get(c);
                score = score * 5 + data.score;
            }

            scores.add(score);

        }
        return scores.stream().sorted().skip(scores.size() / 2).findFirst().orElseThrow();
    }

    private record Data(char opening, long score) { // idk what to call it??????????
    }
}
