package suic.days.day11;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// :( gotta refactor
public class Day11 implements Puzzle<String, Integer> {
    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    @Override
    public Integer solvePart1() {
        List<String> cells = parse();
        int yLength = cells.size();
        int xLength = cells.get(0).length();
        CellState[][] states = parseStates();
        while (true) {
            CellState[][] copy = new CellState[yLength][xLength];
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    int adjacent = countAdjacent(x, y, states);
                    if (adjacent == 0 && states[y][x] == CellState.EMPTY) {
                        copy[y][x] = CellState.FILLED;
                    } else if (adjacent >= 4 && states[y][x] == CellState.FILLED) {
                        copy[y][x] = CellState.EMPTY;
                    } else {
                        copy[y][x] = states[y][x];
                    }
                }
            }
            if (Arrays.deepEquals(states, copy)) {
                break;
            }

            states = copy;
        }
        return countOccupied(states);
    }

    @Override
    public Integer solvePart2() {
        List<String> cells = parse();
        int yLength = cells.size();
        int xLength = cells.get(0).length();
        CellState[][] states = parseStates();
        while (true) {
            CellState[][] copy = new CellState[yLength][xLength];
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    List<CellState> neighbors = getNeighborsInSight(x, y, states);
                    if (states[y][x] == CellState.EMPTY && neighbors.stream()
                            .noneMatch(state -> state == CellState.FILLED)) {
                        copy[y][x] = CellState.FILLED;
                    } else if (states[y][x] == CellState.FILLED && neighbors.stream()
                            .filter(state -> state == CellState.FILLED)
                            .count() > 4) {
                        copy[y][x] = CellState.EMPTY;
                    } else {
                        copy[y][x] = states[y][x];
                    }
                }
            }
            if (Arrays.deepEquals(states, copy)) {
                break;
            }

            states = copy;
        }
        return countOccupied(states);
    }

    public List<CellState> getNeighborsInSight(int x, int y, CellState[][] states) {
        List<CellState> neighbors = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx != 0 || dy != 0) {
                    int tx = x;
                    int ty = y;
                    while (tx + dx >= 0 && tx + dx < states[0].length &&
                            ty + dy >= 0 && ty + dy < states.length) {
                        tx += dx;
                        ty += dy;
                        if (states[ty][tx] != CellState.FLOOR) {
                            neighbors.add(states[ty][tx]);
                            break;
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private int countOccupied(CellState[][] states) {
        int count = 0;
        for (int y = 0; y < states.length; y++) {
            for (int x = 0; x < states[0].length; x++) {
                if (states[y][x] == CellState.FILLED) {
                    count++;
                }
            }
        }
        return count;
    }

    private CellState[][] parseStates() {
        List<String> input = parse();
        CellState[][] states = new CellState[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                states[y][x] = CellState.parseState(input.get(y).charAt(x));
            }
        }
        return states;
    }

    private int countAdjacent(int x, int y, CellState[][] states) {
        int sum = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx != 0 || dy != 0) {
                    if (x + dx >= 0 && x + dx < states[0].length &&
                            y + dy >= 0 && y + dy < states.length) {
                        if (states[dy + y][dx + x] == CellState.FILLED) {
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }
}
