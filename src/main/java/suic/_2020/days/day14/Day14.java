package suic._2020.days.day14;

import suic._2020.Puzzle;
import suic.util.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// :( gotta refactor some duplicate code
public class Day14 implements Puzzle<String, Long> {
    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    private final Pattern MASK_PATTERN = Pattern.compile("mask = (.+)");
    private final Pattern MEMORY_PATTERN = Pattern.compile("mem\\[(\\d+)] = (\\d+)");

    private Map<Long, Long> memory = new HashMap<>();
    private Map<Long, Boolean> floating = new HashMap<>();
    private long maskSet = 0;
    private long maskClear = 0;

    @Override
    public Long solvePart1() {
        List<String> input = parse();
        memory.clear();
        maskSet = 0;
        maskClear = 0;
        for (String line : input) {
            if (line.startsWith("mask")) {
                maskSet = 0;
                maskClear = 0;
                Matcher maskMatcher = MASK_PATTERN.matcher(line);
                maskMatcher.matches();
                char[] masks = maskMatcher.group(1).toCharArray();
                for (char mask : masks) {
                    maskSet *= 2;
                    maskClear *= 2;
                    switch (mask) {
                        case '0' -> maskClear++;
                        case '1' -> maskSet++;
                    }
                }
            } else {
                Matcher memoryMatcher = MEMORY_PATTERN.matcher(line);
                memoryMatcher.matches();
                long address = Long.parseLong(memoryMatcher.group(1));
                long value = Long.parseLong(memoryMatcher.group(2));
                memory.put(address, (value | maskSet) & ~maskClear);
            }
        }
        long total = 0;
        for (long value : memory.values()) {
            total += value;
        }
        return total;
    }

    @Override
    public Long solvePart2() {
        List<String> input = parse();
        maskSet = 0L;
        maskClear = 0L;
        memory.clear();
        floating.clear();
        for (String line : input) {
            if (line.startsWith("mask")) {
                maskSet = 0L;
                maskClear = 0L;
                floating.clear();
                long bitN = 35L;

                Matcher maskMatcher = MASK_PATTERN.matcher(line);
                maskMatcher.matches();
                char[] masks = maskMatcher.group(1).toCharArray();
                for (char mask : masks) {
                    maskSet *= 2L;
                    maskClear *= 2L;
                    switch (mask) {
                        case '0' -> maskClear++;
                        case '1' -> maskSet++;
                        case 'X' -> floating.put(bitN, true);
                    }
                    bitN--;
                }
            } else {
                Matcher memoryMatcher = MEMORY_PATTERN.matcher(line);
                memoryMatcher.matches();
                long address = Long.parseLong(memoryMatcher.group(1));
                long value = Long.parseLong(memoryMatcher.group(2));
                address |= maskSet;
                floating(0L, address, value);
            }
        }
        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    private void floating(long bitN, long address, long value) {
        if (bitN == 36L) {
            memory.put(address, value);
            return;
        }

        if(floating.getOrDefault(bitN, false)) {
            address &= ~(1L << bitN);
            floating(bitN + 1L, address | (1L << bitN), value);
        }
        floating(bitN + 1L, address, value);
    }
}
