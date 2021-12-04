package suic._2021.days.day04;

import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 implements Puzzle<Long> {

    private List<String> input;

    @Override
    public void init() {
        parse();
    }

    @Override
    public void parse() {
        input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");

    }

    public Long solvePart1() {
        String input = String.join("\n", this.input).trim();
        String[] data = input.split("\n\n");
        int[] numbers = Arrays.stream(data[0].split(",")).mapToInt(Integer::parseInt).toArray();
        List<Cell[][]> grids = IntStream.range(1, data.length)
                .mapToObj(i -> createCells(data[i]))
                .toList();
        for (int num : numbers) {
            for (Cell[][] cells : grids) {
                if (markCell(cells, num) && checkCells(cells)) {
                    return computeResult(cells, num);
                }
            }
        }

        return -1L;
    }

    @Override
    public Long solvePart2() {
        String input = String.join("\n", this.input).replaceAll(" +", " ");
        String[] data = input.split("\n\n");
        int[] numbers = Arrays.stream(data[0].split(",")).mapToInt(Integer::parseInt).toArray();
        List<Cell[][]> cells = IntStream.range(1, data.length)
                .mapToObj(i -> createCells(data[i]))
                .collect(Collectors.toCollection(ArrayList::new)); // toCollection(..) as we're gonna modify the list
        for (int num : numbers) {
            for (int i = 0; i < cells.size(); i++) {
                Cell[][] cell = cells.get(i);
                if (markCell(cell, num) && checkCells(cell)) {
                    if (cells.size() == 1) {
                        return computeResult(cell, num);
                    } else {
                        cells.remove(i--);
                    }
                }
            }
        }

        return -1L;
    }

    private long computeResult(Cell[][] cells, int num) {
        long unmarkedSum = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .filter(Predicate.not(Cell::isMarked))
                .mapToLong(Cell::getValue)
                .sum();
        return unmarkedSum * num;
    }

    private boolean markCell(Cell[][] cells, int num) {
        for (Cell[] outer : cells) {
            for (Cell inner : outer) {
                if (inner.getValue() == num) {
                    inner.setMarked(true);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkCells(Cell[][] cells) {
        for (Cell[] cell : cells) {
            if (Arrays.stream(cell).allMatch(Cell::isMarked)) {
                return true;
            }
        }
        for (int i = 0; i < cells[0].length; i++) {
            if (isInvalid(cells, i)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean isInvalid(Cell[][] cells, int i) {
        return IntStream.range(0, cells[i].length).anyMatch(j -> !cells[j][i].isMarked());
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
