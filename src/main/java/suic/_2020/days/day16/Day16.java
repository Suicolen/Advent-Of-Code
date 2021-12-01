package suic._2020.days.day16;

import suic.Puzzle;
import suic.util.FileUtils;
import suic.util.MatchWrapper;
import suic.util.Parser;
import suic.util.Processor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day16 implements Puzzle<String, Long> {
    @Override
    public List<String> parse() {
        return FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }


    @Override
    public Long solvePart1() {
        List<List<String>> input = Processor.split(parse(), String::isBlank);

        List<Rule> rules = new ArrayList<>();
        List<Range> ranges = new ArrayList<>();
        for (String rule : input.get(0)) {
         //   System.out.println(rule);
            MatchWrapper match = Parser.parseMatch("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)", rule);

            Range first = new Range(match.groupInt(2), match.groupInt(3));
            Range second = new Range(match.groupInt(4), match.groupInt(5));
            ranges.add(first);
            ranges.add(second);
            rules.add(new Rule(match.group(1), first, second));
        }
        List<Long> myTickets = new ArrayList<>();
        List<List<Long>> nearbyTickets = new ArrayList<>();
        String type = "";
        for (String line : parse()) {
            if (line.isEmpty()) {
                continue;
            }
            if (line.equals("your ticket:")) {
                type = "your";
                continue;
            } else if (line.equals("nearby tickets:")) {
                type = "nearby";
                continue;
            }

            switch (type) {
                case "your" -> Arrays.stream(line.split(","))
                        .map(Long::parseLong)
                        .forEach(myTickets::add);

                case "nearby" -> nearbyTickets.add(Arrays.stream(line.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()));
            }
        }

        /*int min = rules.stream()
                .map(rule -> Math.min(rule.getFirstLowerBound(), rule.getSecondLowerBound()))
                .mapToInt(Integer::intValue)
                .min()
                .orElse(-1);
        int max = rules.stream()
                .map(rule -> Math.max(rule.getFirstUpperBound(), rule.getSecondUpperBound()))
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);
        List<Integer> invalidNearby =  nearbyTickets.stream().flatMap(List::stream)
                .filter(ticket -> ticket < min || ticket > max)
                .collect(Collectors.toList());*/
        Set<Long> expandedRanges = new HashSet<>();
        rules.forEach(rule -> {
            expandedRanges.addAll(LongStream.rangeClosed(rule.getFirst().lower(), rule.getFirst()
                    .upper())
                    .boxed()
                    .collect(Collectors.toSet()));
            expandedRanges.addAll(LongStream.rangeClosed(rule.getSecond().lower(), rule.getSecond()
                    .upper())
                    .boxed()
                    .collect(Collectors.toSet()));
        });
        List<Long> invalidNearby = new ArrayList<>();
        nearbyTickets.forEach(ticketList -> ticketList.forEach(ticket -> {
            if (!expandedRanges.contains(ticket)) {
                invalidNearby.add(ticket);
            }
        }));


        //System.out.println("invalid nearby: " + invalidNearby);
      //  return -;
        return invalidNearby.stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public Long solvePart2() {
        List<List<String>> input = Processor.split(parse(), String::isBlank);
        List<Rule> rules = new ArrayList<>();
        List<Range> ranges = new ArrayList<>();
        for (String rule : input.get(0)) {
           // System.out.println(rule);
            MatchWrapper match = Parser.parseMatch("([^:]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)", rule);
            Range first = new Range(match.groupInt(2), match.groupInt(3));
            Range second = new Range(match.groupInt(4), match.groupInt(5));
            ranges.add(first);
            ranges.add(second);
            rules.add(new Rule(match.group(1), first, second));
        }
        List<Long> myTickets = new ArrayList<>();
        List<List<Long>> nearbyTickets = new ArrayList<>();
        String type = "";
        for (String line : parse()) {
            if (line.isEmpty()) {
                continue;
            }
            if (line.equals("your ticket:")) {
                type = "your";
                continue;
            } else if (line.equals("nearby tickets:")) {
                type = "nearby";
                continue;
            }

            switch (type) {
                case "your" -> Arrays.stream(line.split(","))
                        .map(Long::parseLong)
                        .forEach(myTickets::add);

                case "nearby" -> nearbyTickets.add(Arrays.stream(line.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()));
            }
        }

       /* int min = rules.stream()
                .map(rule -> Math.min(rule.getFirstLowerBound(), rule.getSecondLowerBound()))
                .mapToInt(Integer::intValue)
                .min()
                .orElse(-1);
        int max = rules.stream()
                .map(rule -> Math.max(rule.getFirstUpperBound(), rule.getSecondUpperBound()))
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);*/


        Map<Integer, Set<Rule>> possible = new HashMap<>();

        //System.out.println("Nearby size: " + nearbyTickets.size());

        List<List<Long>> validTickets = new ArrayList<>();


        for (List<Long> ticket : nearbyTickets) {
            boolean valid = true;
            for (long field : ticket) {
                for (Range range : ranges) {
                    valid = range.inRange(field);
                    if (valid)
                        break;
                }
                if (!valid) {
                    break;
                }
            }
            if (valid)
                validTickets.add(ticket);
        }

        for (Rule selected : rules) {
            for (int i = 0; i < rules.size(); i++) {
                boolean valid = true;
                for (List<Long> ticket : validTickets) {
                    long field = ticket.get(i);
                    valid = selected.getFirst().inRange(field) || selected.getSecond().inRange(field);
                    if (!valid)
                        break;
                }
                if (valid)
                    possible.computeIfAbsent(i, x -> new HashSet<>()).add(selected);
            }
        }

        long val = 1;
        while (!possible.isEmpty()) {
            var iter = possible.entrySet().iterator();
            while (iter.hasNext()) {
                var entry = iter.next();
                int index = entry.getKey();
                Set<Rule> set = entry.getValue();
                if (set.size() == 1) {
                    Rule rule = set.iterator().next();
                    if (rule.getName().startsWith("departure"))
                        val *= myTickets.get(index);
                    iter.remove();
                    possible.forEach((k, v) -> v.remove(rule));
                }
            }
            possible.values().removeIf(Set::isEmpty);
        }

        return val;
    }
}

