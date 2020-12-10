package suic.days.day08;

import suic.Puzzle;
import suic.util.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 implements Puzzle<Operation, Integer> {

    @Override
    public List<Operation> parse() {
        List<String> input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
        List<Operation> operations = new ArrayList<>();
        input.forEach(line -> {
            String[] parts = line.split(" ");
            String instruction = parts[0];
            int value = Integer.parseInt(parts[1]);
            switch (instruction) {
                case "acc" -> operations.add(new Operation(Instruction.ACC, value));
                case "jmp" -> operations.add(new Operation(Instruction.JMP, value));
                case "nop" -> operations.add(new Operation(Instruction.NOP, value));
            }
        });
        return operations;
    }

    @Override
    public Integer solvePart1() {
        List<Operation> operations = parse();
        calculatePart1(operations, part1Seen);
        return acc;
    }

    @Override
    public Integer solvePart2() {
        List<Operation> operations = parse();
        return calculatePart2(operations);
    }

    private int acc = 0;
    private final Set<Integer> part1Seen = new HashSet<>();

    private boolean calculatePart1(List<Operation> operations, Set<Integer> visited) {
        int i = 0;
        acc = 0;
        while (true) {
            if (i >= operations.size()) {
                return true;
            }
            Operation operation = operations.get(i);
            int value = operation.getValue();
            if (visited.add(i)) {
                switch (operation.getInstruction()) {
                    case ACC -> {
                        acc += value;
                        i++;
                    }
                    case JMP -> i += value;
                    case NOP -> i++;
                }
            } else {
                return false;
            }
        }
    }

    private int calculatePart2(List<Operation> operations) {
        for (int visitedIndex : part1Seen) {
            Operation operation = operations.get(visitedIndex);
            List<Operation> copy = new ArrayList<>(operations);
            if (operation.getInstruction() == Instruction.JMP) {
                copy.set(visitedIndex, new Operation(Instruction.NOP, operation.getValue()));
            } else if (operation.getInstruction() == Instruction.NOP) {
                copy.set(visitedIndex, new Operation(Instruction.JMP, operation.getValue()));
            }
            if (calculatePart1(copy, new HashSet<>()))
                return acc;
        }
        return acc;
    }
}
