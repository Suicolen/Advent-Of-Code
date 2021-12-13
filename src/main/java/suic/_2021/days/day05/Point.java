package suic._2021.days.day05;

import static java.lang.Integer.parseInt;

public record Point(int x, int y) {

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public static Point of(String[] s) {
        return new Point(parseInt(s[0]), parseInt(s[1]));
    }
}
