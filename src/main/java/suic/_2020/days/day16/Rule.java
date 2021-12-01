package suic._2020.days.day16;

import lombok.Value;

@Value
public class Rule {

    String name;
    Range first;
    Range second;

    public boolean check(long value) {
        return (value >= first.lower() && value <= first.upper()) || (value >= second.lower() && value <= second
                .upper());
    }

    public boolean isDeparture() {
        return name.startsWith("departure");
    }
}
