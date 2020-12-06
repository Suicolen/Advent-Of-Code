package suic;

public class AdventCalender {

    private static final String BASE_PACKAGE = AdventCalender.class.getPackageName() + ".days.";
    public static final int YEAR = 2020;


    public static void main(String[] args) {
        runAll();
    }

    private static void run() {
      runAll();
    }

    private static void run(int dayOfMonth) {
            try {
                Class<?> clazz = Class.forName(BASE_PACKAGE + pad(dayOfMonth));
                System.out.println("Day " + dayOfMonth + " Solution");
                Puzzle<?, ?> puzzle = (Puzzle<?, ?>) clazz.getDeclaredConstructor().newInstance();
                System.out.println("Part 1 result = " + puzzle.solvePart1());
                System.out.println("Part 2 result = " + puzzle.solvePart2());
            } catch (ClassNotFoundException ignored) {} catch (Exception e) {
                e.printStackTrace();
            }
    }

    private static void runAll() {
        for (int i = 1; i <= 25; i++) {
            run(i);
        }
    }

    public static String pad(int i) {
        return String.format("day%d.Day%d", i, i);
    }
}