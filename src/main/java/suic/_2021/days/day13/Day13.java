package suic._2021.days.day13;

import one.util.streamex.StreamEx;
import suic._2021.Puzzle;
import suic._2021.days.day05.Point;
import suic.util.FileUtils;
import suic.util.StringUtils;

import java.util.*;

import static java.lang.Integer.parseInt;

public class Day13 implements Puzzle<Integer> {

    @Override
    public void init() {
        parse();
    }

    private Set<Point> points;
    private List<Instruction> instructions;

    @Override
    public void parse() {
        List<String> lines = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        int index = lines.indexOf("");
        points = StreamEx.of(lines)
                .limit(index)
                .map(StringUtils::splitAtComma)
                .map(Point::of)
                .toSet();


        instructions = StreamEx.of(lines)
                .skip(index + 1)
                .map(StringUtils::splitAtEquals)
                .map(Instruction::of)
                .toList();
    }

    private Set<Point> solve(Set<Point> points, List<Instruction> folds) {
        return StreamEx.of(folds).foldLeft(points, this::fold);
    }

    private Set<Point> fold(Set<Point> points, Instruction instruction) {
        return StreamEx.of(points).map(p -> {
            if (instruction.horizontal && p.x() > instruction.line) {
                return new Point(instruction.line * 2 - p.x(), p.y());
            } else if (!instruction.horizontal && p.y() > instruction.line) {
                return new Point(p.x(), instruction.line * 2 - p.y());
            }
            return p;
        }).toSet();
    }

    @Override
    public Integer solvePart1() {
        return solve(points, List.of(instructions.get(0))).size();
    }

    @Override
    public Integer solvePart2() {
        Set<Point> r = solve(points, instructions);
        int maxX = r.stream().mapToInt(Point::x).max().orElseThrow();
        int maxY = r.stream().mapToInt(Point::y).max().orElseThrow();

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                Point point = Point.of(x, y);
                System.out.print(r.contains(point) ? "██" : "  ");
            }
            System.out.println();
        }

        return -1;
    }

    record Instruction(boolean horizontal, int line) {
        public static Instruction of(String[] s) {
            return new Instruction(s[0].contains("x"), parseInt(s[1]));
        }
    }
}
