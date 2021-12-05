package suic._2021.days.day05;

import com.svetylkovo.rojo.Rojo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;
import suic._2020.days.day02.Entry;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day05 implements Puzzle<Integer> {

    private static final Pattern LINE_SEGMENT_PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    private List<LineSegment> segments;

    @Override
    public void parse() {
        segments = FileUtils.readResourceAsStream(getClass().getSimpleName() + "Input.txt")
                .map(s -> {
                    Matcher matcher = LINE_SEGMENT_PATTERN.matcher(s);
                    matcher.matches();
                    return LineSegment.of(
                            parseInt(matcher.group(1)),
                            parseInt(matcher.group(2)),
                            parseInt(matcher.group(3)),
                            parseInt(matcher.group(4)));
                }).toList();
    }


    public Integer solvePart1() {
        return getOverlaps(false);
    }

    public Integer solvePart2() {
        return getOverlaps(true);
    }

    private int getOverlaps(boolean includeDiagonals) {
        Set<Point> visited = new HashSet<>();
        Set<Point> overlaps = new HashSet<>();
        for (LineSegment segment : segments) {
            segment.populate(visited, overlaps, includeDiagonals);
        }
        return overlaps.size();
    }
}
