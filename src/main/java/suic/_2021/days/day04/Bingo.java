package suic._2021.days.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Bingo(List<Integer> numbers, List<Cell[][]> boards) {

    public long solve(boolean first) {
       // List<BingoWinner> winners = new ArrayList<>();
        for (int num : numbers) {
            for (int i = 0; i < boards.size(); i++) {
                Cell[][] cells = boards.get(i);
                if (markCell(cells, num) && checkCells(cells)) {
                    if (first) {
                        return computeResult(cells, num);
                    } else {
                        if (boards.size() == 1) {
                            return computeResult(cells, num);
                        } else {
                            boards.remove(i--);
                        }


                    }
                }
            }
        }
        return -1;
        //BingoWinner last = winners.get(winners.size() - 1);
        //return computeResult(last.cells, last.num);
    }

    private boolean markCell(Cell[][] cells, int num) {
        Cell cell = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .filter(c -> c.getValue() == num)
                .findFirst()
                .orElse(null);
        boolean exists = cell != null;
        if (exists) {
            cell.setMarked(true);
        }
        return exists;
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

    private long computeResult(Cell[][] cells, int num) {
        long unmarkedSum = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .filter(Predicate.not(Cell::isMarked))
                .mapToLong(Cell::getValue)
                .sum();
        return unmarkedSum * num;
    }


    private boolean isInvalid(Cell[][] cells, int i) {
        return IntStream.range(0, cells[i].length).anyMatch(j -> !cells[j][i].isMarked());
    }

    private record BingoWinner(Cell[][] cells, int num) {
    }
}
