package suic.days.day4;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 implements Puzzle<String, Long> {

    @Override
    public List<String> parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        List<String> passports = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String line : input) {
            if (!line.isEmpty()) {
                sb.append(line).append(" ");
            } else {
                passports.add(sb.toString().trim());
                sb.setLength(0);
            }
        }
        return passports;
    }


    @Override
    public Long solvePart1() {
        return parse().stream().filter(this::isValidPart1).count();
    }

    private boolean isValidPart1(String str) {
        int count = str.contains("cid:") ? 8 : 7;
        return str.split(" ").length == count;
    }


    @Override
    public Long solvePart2() {
        long count = 0;
        List<String> passports = parse();
        for (String passport : passports) {
            String[] parts = passport.split(" ");
            if (parts.length < 7) { // invalid in the beginning?
                continue;
            }

            parts = Arrays.stream(parts).filter(s -> !s.startsWith("cid")).toArray(String[]::new);

            if (parts.length < 7) { // invalid after filtering? (need for the first validation method)
                continue;
            }
            Arrays.sort(parts, Comparator.comparing(s -> s.substring(0, 3)));
            String joinedValues = Arrays.stream(parts).map(s -> s.substring(4)).collect(Collectors.joining(" "));
            if (part2ValidCombined(joinedValues)) {
                count++;
            }

        }
        return count;
    }

    private final String[] REGEXES = {
            "^(19[2-9]\\d|200[012])$",
            "(amb|blu|brn|gry|grn|hzl|oth)",
            "(202\\d)|(2030)",
            "#([a-fA-F0-9]{6})",
            "^((1[5-8][0-9]|19[0-3])cm)|((59|6[0-9]|7[0-6])in)$",
            "(201\\d)|(2020)",
            "\\d{9}"
    };

    private final String COMBINED_REGEX = "(19[2-9]\\d|200[012]) (amb|blu|brn|gry|grn|hzl|oth) (202\\d|(2030)) #([a-fA-F0-9]{6}) ((1[5-8][0-9]|19[0-3])cm|((59|6[0-9]|7[0-6])in)) (201\\d|(2020)) \\d{9}";


    private boolean part2Valid(String[] passports) {
        return IntStream.range(0, passports.length).allMatch(i -> passports[i].split(":")[1].matches(REGEXES[i]));
    }

    private boolean part2ValidCombined(String str) {
        Pattern p = Pattern.compile(COMBINED_REGEX);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}