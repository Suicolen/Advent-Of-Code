package suic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern pattern = Pattern.compile("(\\d+,\\d+)");

    /**
     * Parses each line from the input list using the provided pattern and {@link Matcher#matches()}.
     *
     * @param pattern The pattern to use for matching to each line.
     * @param lines The list of lines to parse.
     * @param consumer The consumer to use the matcher and line data returned for each line.
     */
    public static void parseLines(Pattern pattern, List<String> lines, BiConsumer<Matcher, String> consumer) {
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            m.matches();
            consumer.accept(m, line);
        }
    }

    /**
     * Parses each line from the input list using the provided pattern and {@link Matcher#find}.
     *
     * @param pattern The pattern to use for matching to each line.
     * @param lines The list of lines to parse.
     * @param consumer The consumer to use the matcher and line data returned for each line.
     */
    public static void parseLinesFind(Pattern pattern, List<String> lines, BiConsumer<Matcher, String> consumer) {
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            m.find();
            consumer.accept(m, line);
        }
    }


    public static MatchWrapper parseMatch(String regex, String input) {
        return parseMatch(Pattern.compile(regex).matcher(input));
    }

    /**
     * Creates a {@link Matcher} from the provided {@link Pattern} and {@link String} input.
     * The returned value will be a {@link MatchWrapper} that wraps a {@link MatchResult}
     * with overloaded helper functions.
     *
     * @param pattern The {@link Pattern} used to match the provided input.
     * @param input The {@link String} input to be matched against the pattern.
     * @return A {@link MatchWrapper} that wraps a {@link MatchResult}
     */
    public static MatchWrapper parseMatch(Pattern pattern, String input) {
        return parseMatch(pattern.matcher(input));
    }

    public static MatchWrapper parseMatch(Matcher matcher) {
        if (!matcher.matches())
            throw new IllegalStateException("Matcher does not match the full input string");
        return new MatchWrapper(matcher.toMatchResult());
    }
}