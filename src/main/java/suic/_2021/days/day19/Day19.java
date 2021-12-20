package suic._2021.days.day19;

import one.util.streamex.StreamEx;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import suic._2021.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Day19 implements Puzzle<Integer> {

    private final List<Scanner> scanners = new ArrayList<>();
    private List<Scanner> aligned;

    @Override
    public void parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        Scanner scanner = new Scanner();
        for (String line : input) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("---")) {
                scanner = new Scanner();
                scanners.add(scanner);
                continue;
            }

            String[] data = line.split(",");
            Vector3i pos = new Vector3i(parseInt(data[0]), parseInt(data[1]), parseInt(data[2]));
            scanner.beacons.add(pos);
        }
    }

    @Override
    public void init() {
        parse();
        aligned = align(scanners);
    }

    @Override
    public Integer solvePart1() {
        return StreamEx.of(aligned).flatMap(s -> s.beacons.stream()).toSet().size();
    }

    @Override
    public Integer solvePart2() {
        int maxDist = Integer.MIN_VALUE;
        for (Scanner a : aligned) {
            for (Scanner b : aligned) {
                if (a == b) {
                    continue;
                }
                int dist = a.distanceTo(b);
                if (dist > maxDist) {
                    maxDist = dist;
                }
            }
        }
        return maxDist;
    }


    private List<Scanner> align(List<Scanner> scanners) {
        List<Scanner> original = new ArrayList<>(scanners);
        List<Scanner> aligned = new ArrayList<>();
        Scanner first = original.remove(0);
        aligned.add(first);

        while (!original.isEmpty()) {
            tryAlign(original, aligned);
        }
        return aligned;
    }

    private void tryAlign(List<Scanner> original, List<Scanner> aligned) {
        for (Scanner s : aligned) {
            for (Scanner o : original) {
                for (Scanner orientation : orientations(o)) {
                    Vector3i align = align(s, orientation);
                    if (align != null) {
                        Scanner sAligned = orientation.translate(align);
                        original.remove(o);
                        aligned.add(sAligned);
                        return;
                    }
                }
            }
        }
    }

    private Vector3i align(Scanner s1, Scanner s2) {
        List<Vector3i> s1s2Vectors = s1.beacons.stream()
                .flatMap(b1 -> s2.beacons
                        .stream()
                        .filter(b2 -> b1 != b2)
                        .map(b1::sub)).toList();

        Map<Integer, Long> x = s1s2Vectors.stream()
                .collect(Collectors.groupingBy(v -> v.x, Collectors.counting()));
        Map<Integer, Long> y = s1s2Vectors.stream()
                .collect(Collectors.groupingBy(v -> v.y, Collectors.counting()));
        Map<Integer, Long> z = s1s2Vectors.stream()
                .collect(Collectors.groupingBy(v -> v.z, Collectors.counting()));


        Map.Entry<Integer, Long> xMax = x.entrySet()
                .stream()
                .max((a, b) -> (int) (a.getValue() - b.getValue()))
                .orElseThrow();
        Map.Entry<Integer, Long> yMax = y.entrySet()
                .stream()
                .max((a, b) -> (int) (a.getValue() - b.getValue()))
                .orElseThrow();
        Map.Entry<Integer, Long> zMax = z.entrySet()
                .stream()
                .max((a, b) -> (int) (a.getValue() - b.getValue()))
                .orElseThrow();


        long min = LongStream.of(xMax.getValue(), yMax.getValue(), zMax.getValue())
                .min()
                .orElseThrow();

        return min >= 12 ? new Vector3i(xMax.getKey(), yMax.getKey(), zMax.getKey()) : null;
    }

    private List<Scanner> orientations(Scanner s) {
        return rotateScanners(rotateScanners(rotateScanners(Stream.of(s), rX), rY), rZ).toList(); // die
    }

    private Stream<Scanner> rotateScanners(Stream<Scanner> scanners, float[] m) {
        return scanners.flatMap(s -> rotateScanners(s, m));
    }

    private Stream<Scanner> rotateScanners(Scanner s, float[] m) {
        Set<Scanner> rotated = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            s = s.rotate(m);
            rotated.add(s);
        }
        return rotated.stream();
    }

    private static class Scanner {
        private final List<Vector3i> beacons;
        private final Vector3i pos;

        public Scanner() {
            beacons = new ArrayList<>();
            pos = new Vector3i(0, 0, 0);
        }

        public Scanner(List<Vector3i> beacons, Vector3i pos) {
            this.beacons = beacons;
            this.pos = pos;
        }

        public int distanceTo(Scanner other) {
            return pos.distanceTo(other.pos);
        }

        private Scanner rotate(float[] m) {
            return new Scanner(beacons.stream()
                    .map(b -> b.rotate(m))
                    .toList(), pos);
        }

        private Scanner translate(Vector3i location) {
            return new Scanner(beacons.stream()
                    .map(b -> b.translate(location))
                    .toList(), location);
        }
    }

    record Vector3i(int x, int y, int z) {

        private Vector3i rotate(float[] m) {
            Vector3f rotated = new Vector3f();
            new Matrix3f().set(m).transform(x, y, z, rotated);
            return new Vector3i((int) rotated.x, (int) rotated.y, (int) rotated.z);
        }

        private Vector3i translate(Vector3i point) {
            return new Vector3i(x + point.x, y + point.y, z + point.z);
        }

        private Vector3i sub(Vector3i v) {
            return new Vector3i(x - v.x, y - v.y, z - v.z);
        }

        private int distanceTo(Vector3i v) {
            return Math.abs(x - v.x) + Math.abs(y - v.y) + Math.abs(z - v.z);
        }
    }

    private final float[] rX = {
            1, 0, 0,
            0, 0, -1,
            0, 1, 0
    };

    private final float[] rY = {
            0, 0, 1,
            0, 1, 0,
            -1, 0, 0
    };

    private final float[] rZ = {
            0, -1, 0,
            1, 0, 0,
            0, 0, 1
    };
}