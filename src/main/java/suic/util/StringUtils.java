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

    public static byte[] hexStringToByteArray(final String s) {
        int len = s.length() / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            long digit1 = Long.parseLong(String.valueOf(s.charAt(i * 2)), 16);
            long digit2 = Long.parseLong(String.valueOf(s.charAt(i * 2 + 1)), 16);
            byte b = (byte) (digit1 << 4 | digit2);
            bytes[i] = b;
        }
        return bytes;
    }
}
