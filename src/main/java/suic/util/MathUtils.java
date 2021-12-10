package suic.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {
    public static float map(float value, float iStart, float iStop, float oStart, float oStop) {
        return oStart + (oStop - oStart) * ((value - iStart) / (iStop - iStart));
    }
}
