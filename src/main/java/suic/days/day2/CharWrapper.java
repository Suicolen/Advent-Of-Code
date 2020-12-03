package suic.days.day2;

import java.util.function.Function;

public class CharWrapper implements Function<String, Character> {
    @Override
    public Character apply(String str) {
        return str.charAt(0);
    }
}