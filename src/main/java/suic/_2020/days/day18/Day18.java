package suic._2020.days.day18;

import suic._2020.Puzzle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 implements Puzzle<String, Long> {

    @Override
    public List<String> parse() {
        return null;
    }

    @Override
    public Long solvePart1() {
        //((1 + 2) * (3) + (4)) * 5 + 6
        String str = "1 + 2 * 3 + 4 * 5 + 6";
        // double expression = new ExpressionBuilder(str).build().evaluate();
        solve(str);
        // System.out.println(expression);

        return null;
    }

    private long solve(String string) {
        String[] split = string.split(" ");
        List<String[]> arrays = new ArrayList<>();
        int chunk = 3; // chunk size to divide
        for (int i = 0; i < split.length; i += chunk) {
            arrays.add(Arrays.copyOfRange(split, i, Math.min(split.length, i + chunk)));
        }
        arrays.forEach(x -> {
            System.out.println(Arrays.toString(x));
        });

        int result = 0;
        StringBuilder end = new StringBuilder();
        while(arrays.size() > 1) {
            String str = String.join("", arrays.get(0));
            end.append("(");
            end.append(str);
            end.append(")");
            arrays.remove(0);
        }
        System.out.println(end.toString());

        //int result = 0;
       // for (String[] arr : arrays) {
            //String str = String.join("", arr);
           // result += (int) new ExpressionBuilder(str).build().evaluate();
      //  }
        //String str = String.join("", Arrays.copyOfRange(split,0, 3));
        //int sum = (int) new ExpressionBuilder(str).build().evaluate();
        //System.out.println(sum);
        return -1L;
        //return tokens[0];
    }

    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    @Override
    public Long solvePart2() {
        return null;
    }
}
