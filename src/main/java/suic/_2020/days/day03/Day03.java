package suic._2020.days.day03;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.List;
import java.util.stream.Stream;

public class Day03 implements Puzzle<String, Long> {

    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    private long countTrees(List<String> input, int right, int down) {
        int trees = 0;
        int x = 0;
        for (int y = 0; y < input.size(); y += down) {
            String line = input.get(y);
            if (line.charAt(x) == '#') {
                trees++;
            }
            x = (x + right) % line.length();
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
        Stream<GridCoordinate> coordinates = Stream.of(
                new GridCoordinate(1, 1),
                new GridCoordinate(3, 1),
                new GridCoordinate(5, 1),
                new GridCoordinate(7, 1),
                new GridCoordinate(1, 2)
        );


        return coordinates.mapToLong(c -> countTrees(input, c.getX(), c.getY()))
                .reduce(1, (a, b) -> a * b);
    }
}
