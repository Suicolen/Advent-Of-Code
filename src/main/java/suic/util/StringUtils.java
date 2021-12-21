package suic.util;

import lombok.experimental.UtilityClass;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StringUtils {
    public static String[] splitAtComma(String s) {
        return s.split(",");
    }

    public static String[] splitAtEquals(String s) {
        return s.split("=");
    }

    public static List<Character> toCharList(String str) {
        return IntStreamEx.ofChars(str).mapToObj(c -> (char) c).toMutableList();
    }

    public static boolean[] toBooleanArray(String str) {
        boolean[] array = new boolean[str.length()];
        for (int i = 0; i < array.length; i++) {
            array[i] = str.charAt(i) == '#';
        }
        return array;
    }

    public static BitSet toBitSet(String str) {
        BitSet bitSet = new BitSet(str.length());
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '#') {
                bitSet.set(i);
            }
        }
        return bitSet;
    }
}
