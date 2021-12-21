package suic.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LongPair {
    private long left;
    private long right;

    public void addLeft(long value) {
        left += value;
    }

    public void addRight(long value) {
        right += value;
    }

    public void add(LongPair pair) {
        left += pair.left;
        right += pair.right;
    }

    public void add(long left, long right) {
        this.left += left;
        this.right += right;
    }

}
