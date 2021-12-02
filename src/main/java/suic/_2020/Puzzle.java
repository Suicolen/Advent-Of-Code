package suic._2020;

import java.util.List;

public interface Puzzle<I, O> {
    List<I> parse();
    O solvePart1();
    O solvePart2();
}