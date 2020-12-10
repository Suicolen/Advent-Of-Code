package suic;

/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

public class AdventCalender {

    private static final String BASE_PACKAGE = AdventCalender.class.getPackageName() + ".days.";
    public static final int YEAR = 2020;


    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("-----------------------------------------------------------");
        runAll();
    }

    private static void run(int dayOfMonth) {
        try {
            Class<?> clazz = Class.forName(BASE_PACKAGE + pad(dayOfMonth));
            System.out.println("Day " + dayOfMonth + " Solution");
            Puzzle<?, ?> puzzle = (Puzzle<?, ?>) clazz.getDeclaredConstructor().newInstance();
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
        return String.format("day%02d.Day%d", i, i);
    }
}