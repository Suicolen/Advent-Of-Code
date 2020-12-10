package suic.days.day08;


import lombok.Value;

@Value
public class Operation {
    Instruction instruction;
    int value;
}
