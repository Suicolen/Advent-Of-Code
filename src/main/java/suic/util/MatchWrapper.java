package suic.util;

import lombok.RequiredArgsConstructor;

import java.util.regex.MatchResult;

/**
 * A wrapper of a {@link MatchResult} with overloaded helper functions like
 * {@link #groupInt(int)} and {@link #groupLong(int)}
 */
@RequiredArgsConstructor
public class MatchWrapper implements MatchResult {
    private final MatchResult result;

    @Override
    public int start() {
        return result.start();
    }

    @Override
    public int start(int group) {
        return result.start(group);
    }

    @Override
    public int end() {
        return result.end();
    }

    @Override
    public int end(int group) {
        return result.end(group);
    }

    @Override
    public String group() {
        return result.group();
    }

    @Override
    public String group(int group) {
        return result.group(group);
    }

    /**
     * Returns the captured group from the group index, parsed as an integer.
     *
     * @param group The index of a capturing group in this matcher's pattern.
     * @throws NumberFormatException If the string does not contain a parsable integer.
     * @return The captured group parsed as an integer.
     */
    public int groupInt(int group) {
        return Integer.parseInt(group(group));
    }

    /**
     * Returns the captured group from the group index, parsed as a long.
     *
     * @param group The index of a capturing group in this matcher's pattern.
     * @throws NumberFormatException If the string does not contain a parsable long.
     * @return The captured group parsed as a long.
     */
    public long groupLong(int group) {
        return Long.parseLong(group(group));
    }

    @Override
    public int groupCount() {
        return result.groupCount();
    }
}