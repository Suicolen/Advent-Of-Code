package suic._2021.days.day22;

import one.util.streamex.StreamEx;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class Day22 implements Puzzle<Long> {

    private List<Cube> cubes;
    private final Pattern CUBE_PATTERN = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    @Override
    public void parse() {
        cubes = FileUtils.readResourceAsStream(getClass().getSimpleName() + "Input.txt")
                .map(this::parseCube)
                .toList();
    }

    private Cube parseCube(String line) {
        Matcher matcher = CUBE_PATTERN.matcher(line);
        matcher.matches();
        boolean on = matcher.group(1).equals("on");
        long startX = parseLong(matcher.group(2));
        long endX = parseLong(matcher.group(3));

        long startY = parseLong(matcher.group(4));
        long endY = parseLong(matcher.group(5));

        long startZ = parseLong(matcher.group(6));
        long endZ = parseLong(matcher.group(7));

        return new Cube(on, startX, endX, startY, endY, startZ, endZ);
    }

    @Override
    public Long solvePart1() {
        Set<Vector3L> on = new HashSet<>();
        cubes.forEach(cube -> {
            for (long x = max(-50, cube.startX); x <= min(50, cube.endX); x++) {
                for (long y = max(-50, cube.startY); y <= min(50, cube.endY); y++) {
                    for (long z = max(-50, cube.startZ); z <= min(50, cube.endZ); z++) {
                        if (cube.on) {
                            on.add(new Vector3L(x, y, z));
                        } else {
                            on.remove(new Vector3L(x, y, z));
                        }
                    }
                }
            }
        });

        return (long) on.size();
    }

    @Override
    public Long solvePart2() {
        List<Cube> result = new ArrayList<>();
        cubes.forEach(cube -> {
            List<Cube> intersections = StreamEx.of(result)
                    .map(cube::intersect)
                    .filter(Objects::nonNull)
                    .toMutableList();
            if (cube.on) {
                intersections.add(cube);
            }
            result.addAll(intersections);
        });

        return StreamEx.of(result)
                .foldLeft(0L, (cur, cube) -> cur + cube.volume() * (cube.on ? 1 : -1));
    }

    private record Cube(boolean on, long startX, long endX, long startY, long endY,
                        long startZ, long endZ) {

        private Cube intersect(Cube other) {
            if (startX > other.endX || endX < other.startX || startY > other.endY || endY < other.startY || startZ > other.endZ || endZ < other.startZ) {
                return null;
            }

            return new Cube(!other.on,
                    max(startX, other.startX), min(endX, other.endX),
                    max(startY, other.startY), min(endY, other.endY),
                    max(startZ, other.startZ), min(endZ, other.endZ));
        }

        private long volume() {
            return (endX - startX + 1L) * (endY - startY + 1L) * (endZ - startZ + 1L);
        }

    }

    private record Vector3L(long x, long y, long z) {
    }
}
