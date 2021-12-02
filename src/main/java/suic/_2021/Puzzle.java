package suic._2021;

import java.util.List;

public interface Puzzle<I, O> {
    void parse();
    O solvePart1();
    O solvePart2();
    default void init() {
        parse();
    }
}