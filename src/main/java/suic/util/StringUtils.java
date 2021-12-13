package suic.util;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class StringUtils {
    public static String[] splitAtComma(String s) {
        return s.split(",");
    }

    public static String[] splitAtEquals(String s) {
        return s.split("=");
    }
}
