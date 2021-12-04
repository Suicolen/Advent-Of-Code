package suic._2021;

public interface Puzzle<O> {
    void parse();
    O solvePart1();
    O solvePart2();
    default void init() {
        parse();
    }
}