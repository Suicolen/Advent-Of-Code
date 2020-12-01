package suic;

import java.util.List;

public interface Puzzle<T> {
    T solvePart1(List<T> input);
    T solvePart2(List<T> input);
}
