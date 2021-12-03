package suic;

import suic._2021.Puzzle;

public class AdventCalender {

    public static final int YEAR = 2021;
    private static final String BASE_PACKAGE = AdventCalender.class.getPackageName() + "." +  "_" + YEAR + ".days.";


    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("-----------------------------------------------------------");
        run(3);

    }

    private static void run(int dayOfMonth) {
        try {
            Class<?> clazz = Class.forName(BASE_PACKAGE + pad(dayOfMonth));
            System.out.println("Day " + dayOfMonth + " Solution");
            Puzzle<?, ?> puzzle = (Puzzle<?, ?>) clazz.getDeclaredConstructor().newInstance(); // 2021 for now
            puzzle.init();
            System.out.println("Part 1 result = " + puzzle.solvePart1());
            System.out.println("Part 2 result = " + puzzle.solvePart2());
            System.out.println("-----------------------------------------------------------");

        } catch (ClassNotFoundException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("-----------------------------------------------------------");
    }

    private static void runAll() {
        for (int i = 1; i <= 25; i++) {
            run(i);
        }
    }

    private static void runRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            run(i);
        }
    }

    public static String pad(int i) {
        return String.format("day%02d.Day%02d", i, i);
    }
}