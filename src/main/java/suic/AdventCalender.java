package suic;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AdventCalender {

    private static final String BASE_PACKAGE = AdventCalender.class.getPackageName() + ".days.";

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        runAll();
    }

    private static void run(int dayOfMonth) {
            try {
                Class<?> clazz = Class.forName(BASE_PACKAGE + pad(dayOfMonth));
                System.out.println(clazz.getSimpleName());
                Puzzle<?, ?> puzzle = (Puzzle<?, ?>) clazz.getDeclaredConstructor().newInstance();
                System.out.println("Part 1 result = " + puzzle.solvePart1());
                System.out.println("Part 2 result = " + puzzle.solvePart2());
            } catch (ClassNotFoundException ignored) {} catch (Exception e) {
                e.printStackTrace();
            }
    }

    private static void runAll() {
        for (int i = 1; i <= 2; i++) {
            run(i);
        }
    }

    private static String pad(int i) {
        return String.format("day%d.Day%d", i, i);
    }
}