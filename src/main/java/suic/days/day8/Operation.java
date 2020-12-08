package suic.days.day8;


import lombok.Value;

@Value
public class Operation {
    Instruction instruction;
    int value;
}
