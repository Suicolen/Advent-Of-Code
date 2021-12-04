package suic._2021.days.day04;

import one.util.streamex.IntStreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day04 implements Puzzle<Long> {

    private Bingo bingo;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        String[] input = FileUtils.readResourceAsStream(getClass().getSimpleName() + "Input.txt")
                .collect(Collectors.joining("\n"))
                .split("\n\n");
        List<Integer> numbers = Arrays.stream(input[0].split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
        List<Cell[][]> boards = IntStreamEx.range(1, input.length)
                .mapToObj(i -> createCells(input[i]))
                .toMutableList();
        bingo = new Bingo(numbers, boards);
    }

    public Long solvePart1() {
        return bingo.solve(true);
    }

    @Override
    public Long solvePart2() {
        return bingo.solve(false);
    }

    private Cell[][] createCells(String str) {
        String[] lines = str.split("\n");
        Cell[][] cells = new Cell[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            cells[i] = Arrays.stream(lines[i].split(" "))
                    .filter(Predicate.not(String::isEmpty))
                    .map(s -> new Cell(Integer.parseInt(s)))
                    .toArray(Cell[]::new);
        }
        return cells;
    }
}