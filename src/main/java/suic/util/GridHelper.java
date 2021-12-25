package suic.util;

import java.util.Arrays;
import java.util.List;

public class GridHelper {
    public static int[][] parseAsInt(List<String> lines) {
        int length = lines.get(0).length();
        int[][] grid = new int[lines.size()][length];
        for (int x = 0; x < lines.size(); x++) {
            for (int y = 0; y < length; y++) {
                grid[x][y] = Character.getNumericValue(lines.get(x).charAt(y));
            }
        }
        return grid;
    }

    public static char[][] parseAsChar(List<String> lines) {
        char[][] grid = new char[lines.size()][];
        for (int y = 0; y < lines.size(); y++) {
            int len = lines.get(y).length();
            grid[y] = new char[len];
            for (int x = 0; x < len; x++) {
                grid[y][x] = lines.get(y).charAt(x);
            }
        }
        return grid;
    }

    public static char[][] deepCopy(char[][] grid) {
        return Arrays.stream(grid).map(char[]::clone).toArray(x -> grid.clone());
    }
}
