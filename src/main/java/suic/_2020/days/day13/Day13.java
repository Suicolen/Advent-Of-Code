package suic._2020.days.day13;

import suic._2020.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

// :( gotta refactor crt
public class Day13 implements Puzzle<String, Long> {
    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    @Override
    public Long solvePart1() {
        List<String> input = parse();
        int timestamp = Integer.parseInt(input.get(0));
        List<Integer> busIds = new ArrayList<>();
        String[] data = input.get(1).split(",");
        for (String str : data) {
            if (!str.equals("x")) {
                busIds.add(Integer.parseInt(str));
            } else {
                busIds.add(0);
            }
        }
        long result = 0;
        int earliest = Integer.MAX_VALUE;
        for (int busId : busIds) {
            if (busId == 0) {
                continue;
            }
            int departTime = busId * (timestamp / busId + 1);
            if (departTime < earliest) {
                earliest = departTime;
                result = busId;
            }
        }

        return result * (earliest - timestamp);
    }

    @Override
    public Long solvePart2() {
        List<String> input = parse();
        String[] data = input.get(1).split(",");
        List<Integer> rems = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        int i = 0;
        int index = 0;
        for (String str : data) {
            if (!str.equals("x")) {
                rems.add(-index);
                nums.add(Integer.parseInt(str));

                while (rems.get(i) < 0) {
                    rems.set(i, rems.get(i) + nums.get(i));
                }
                i++;
            }
            index++;
        }
        return findMinX(nums.stream().mapToInt(Integer::intValue).toArray(), rems.stream()
                .mapToInt(Integer::intValue)
                .toArray());
    }


    public static long inv(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1)
            return 0;

        // Apply extended Euclid Algorithm
        while (a > 1) {
            // q is quotient
            q = a / m;

            t = m;

            // m is remainder now, process
            // same as euclid's algo
            m = a % m;
            a = t;

            t = x0;

            x0 = x1 - q * x0;

            x1 = t;
        }

        // Make x1 positive
        if (x1 < 0)
            x1 += m0;

        return x1;
    }

    public static long findMinX(int[] num, int[] rem) {
        // Compute product of all numbers
        long prod = 1;
        int k = num.length;
        for (int i = 0; i < k; i++)
            prod *= num[i];

        // Initialize result
        long result = 0;

        // Apply above formula
        for (int i = 0; i < k; i++) {
            long pp = prod / num[i];
            result += rem[i] * inv(pp, num[i]) * pp;
        }
        return result % prod;
    }
}
