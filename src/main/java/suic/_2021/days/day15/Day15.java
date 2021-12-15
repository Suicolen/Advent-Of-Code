package suic._2021.days.day15;


import suic._2021.Puzzle;
import suic._2021.days.day05.Point;
import suic.util.FileUtils;
import suic.util.GridHelper;

import java.util.*;

public class Day15 implements Puzzle<Integer> {

    @Override
    public void init() {
        parse();
    }


    private int[][] grid;
    private int[][] cost;

    private int rows;
    private int cols;

    private final Point[] ADJACENT = {Point.of(0, 1), Point.of(0, -1), Point.of(1, 0), Point.of(-1, 0)};

    @Override
    public void parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        grid = GridHelper.parseAsInt(input);
        rows = grid.length;
        cols = grid[0].length;
        cost = new int[rows * 5][cols * 5];
    }

    public Integer solvePart1() {
        return solve(false);
    }

    public Integer solvePart2() {
        return solve(true);
    }

    private int solve(boolean part2) { // dijkstra
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, Point.of(0, 0)));
        Set<Point> visited = new HashSet<>();
        int rows = this.rows * (part2 ? 5 : 1);
        int cols = this.cols * (part2 ? 5 : 1);
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            Point p = node.point;
            if (visited.contains(p)) {
                continue;
            }

            int row = p.x();
            int col = p.y();

            visited.add(p);

            cost[row][col] = node.cost;

            if (row == rows - 1 && col == cols - 1) {
                break;
            }

            for (Point point : ADJACENT) {
                int r = row + point.x();
                int c = col + point.y();

                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    pq.add(new Node(node.cost + (part2 ? get(r, c) : grid[r][c]), Point.of(r, c)));
                }
            }
        }

        return cost[rows - 1][cols - 1];
    }

    private int get(int row, int col) {
        int x = (grid[row % rows][col % cols] + (row / rows) + (col / cols));
        return (x - 1) % 9 + 1;
    }

    private record Node(int cost, Point point) implements Comparable<Node> {
        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}
