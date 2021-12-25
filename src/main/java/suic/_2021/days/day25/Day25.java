package suic._2021.days.day25;

import suic._2021.Puzzle;
import suic.util.FileUtils;
import suic.util.GridHelper;

import java.util.List;

public class Day25 implements Puzzle<Integer> {

    private char[][] grid;
    private int rows;
    private int cols;

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        grid = GridHelper.parseAsChar(lines);
        rows = grid.length;
        cols = grid[0].length;
    }

    @Override
    public Integer solvePart1() {
        return solve(0);
    }

    private int solve(int steps) {
        if (grid == null) {
            return steps;
        }

        grid = step(grid);
        return solve(steps + 1);
    }

    private char[][] step(char[][] board) {
        char[][] east = move(board, '>');
        if (east != null) {
            board = east;
        }

        char[][] south = move(board, 'v');
        if (south != null) {
            board = south;
        }

        return east == null && south == null ? null : board;
    }

    private char[][] move(char[][] board, char type) {
        boolean moved = false;
        char[][] newBoard = GridHelper.deepCopy(board);
        int dx = type == 'v' ? 1 : 0;
        int dy = type == 'v' ? 0 : 1;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                char current = board[x][y];
                if (current == type) {
                    int newX = (x + dx) % rows;
                    int newY = (y + dy) % cols;
                    if (board[newX][newY] != '>' && board[newX][newY] != 'v') {
                        moved = true;
                        newBoard[x][y] = '.';
                        newBoard[newX][newY] = current;
                    }
                }
            }
        }
        return moved ? newBoard : null;
    }

    @Override
    public Integer solvePart2() {
        return -1;
    }
}
