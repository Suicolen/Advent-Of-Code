package suic.util;

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
}
