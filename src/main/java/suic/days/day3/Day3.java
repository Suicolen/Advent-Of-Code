package suic.days.day3;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.Arrays;
import java.util.List;

public class Day3 implements Puzzle<String, Long> {

    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    private long countTrees(List<String> input, int right, int down) {
        int trees = 0;
        int position = 0;
        for (int y = 0; y < input.size(); y += down) {
            String line = input.get(y);
            if (line.charAt(position) == '#') {
                trees++;
            }
            position = (position + right) % line.length();
        }
        return trees;
    }

    @Override
    public Long solvePart1() {
        List<String> input = parse();
        return countTrees(input, 3, 1);
    }

    @Override
    public Long solvePart2() {
        List<String> input = parse();
        List<GridCoordinate> coordinates = List.of(
                new GridCoordinate(1, 1),
                new GridCoordinate(3, 1),
                new GridCoordinate(5, 1),
                new GridCoordinate(7, 1),
                new GridCoordinate(1, 2)
        );
        return coordinates.stream().mapToLong(c -> countTrees(input, c.getX(), c.getY())).reduce(1, (a, b) -> a * b);
    }
}
