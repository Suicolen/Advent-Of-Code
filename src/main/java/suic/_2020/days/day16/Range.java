package suic._2020.days.day16;

public record Range(long lower, long upper) {
    public boolean inRange(long value) {
        return value >= lower && value <= upper;
    }
}
