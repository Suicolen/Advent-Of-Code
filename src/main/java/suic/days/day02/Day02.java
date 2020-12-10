package suic.days.day02;

import com.svetylkovo.rojo.Rojo;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.List;

public class Day02 implements Puzzle<Entry, Long> {

    @Override
    public List<Entry> parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        return Rojo.of(Entry.class).matchList(String.join(System.lineSeparator(), input));
    }

    public boolean isValid(Entry entry, boolean firstPart) {
        int lower = entry.getLowerBound();
        int upper = entry.getUpperBound();
        char c = entry.getCharacter();
        String password = entry.getPassword();
        if (firstPart) {
            long count = password.chars().filter(ch -> ch == c).count();
            return count >= lower && count <= upper;
        } else {
            return (password.charAt(lower - 1) == c) ^ (password.charAt(upper - 1) == c);
        }
    }

    @Override
    public Long solvePart1() {
        return parse().stream().filter(entry -> isValid(entry, true)).count();
    }

    @Override
    public Long solvePart2() {
        return parse().stream().filter(entry -> isValid(entry, false)).count();
    }
}
