package suic._2021.days.day05;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.*;
import static java.util.function.Predicate.not;

public record LineSegment(Point start, Point end) {

    public static LineSegment of(int x1, int y1, int x2, int y2) {
        return new LineSegment(new Point(x1, y1), new Point(x2, y2));
    }

    public void populate(Set<Point> visited, Set<Point> overlaps, boolean includeDiagonals) {
        if (start.y() == end.y()) {
            populateVertical(visited, overlaps);
        } else if (start.x() == end.x()) {
            populateHorizontal(visited, overlaps);
        } else if (includeDiagonals) {
            populateDiagonal(visited, overlaps);
        }
    }

    private void populateVertical(Set<Point> visited, Set<Point> overlaps) {
        int minX = min(start.x(), end.x());
        int maxX = max(start.x(), end.x());
        int y = start.y();
        IntStream.rangeClosed(minX, maxX)
                .mapToObj(x -> new Point(x, y))
                .filter(not(visited::add))
                .forEach(overlaps::add);
    }

    private void populateHorizontal(Set<Point> visited, Set<Point> overlaps) {
        int minY = min(start.y(), end.y());
        int maxY = max(start.y(), end.y());
        int x = start.x();
        IntStream.rangeClosed(minY, maxY)
                .mapToObj(y -> new Point(x, y))
                .filter(not(visited::add))
                .forEach(overlaps::add);
    }

    private void populateDiagonal(Set<Point> visited, Set<Point> overlaps) {
        int dx = start.x() < end.x() ? 1 : -1;
        int dy = start.y() < end.y() ? 1 : -1;
        int endX = end.x() + dx;
        int x = start.x();
        int y = start.y();
        Stream.iterate(new Point(x, y), p -> p.x() != endX, p -> new Point(p.x() + dx, p.y() + dy))
                .filter(not(visited::add))
                .forEach(overlaps::add);
    }
}
