package suic.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public static String[] splitAtComma(String s) {
        return s.split(",");
    }

    public static String[] splitAtEquals(String s) {
        return s.split("=");
    }
}
