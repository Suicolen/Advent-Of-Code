package suic.days.day2;

import com.svetylkovo.rojo.Rojo;
import suic.Puzzle;
import suic.util.FileUtils;

import java.util.List;

public class Day2 implements Puzzle<Entry, Long> {

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        System.out.println("Part 1 = " + day2.solvePart1());
        System.out.println("Part 2 = " + day2.solvePart2());
    }

    @Override
    public List<Entry> parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        return Rojo.of(Entry.class).matchList(String.join(System.lineSeparator(), input));
    }

    public boolean isValid(Entry entry, boolean firstPart) {
        int min = entry.getMin();
        int max = entry.getMax();
        char c = entry.getCharacter();
        String password = entry.getPassword();
        if (firstPart) {
            int count = (int) password.chars().filter(ch -> ch == c).count();
            return count >= min && count <= max;
        } else {
            return (password.charAt(min - 1) == c) ^ (password.charAt(max - 1) == c);
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
