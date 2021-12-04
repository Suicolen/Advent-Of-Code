package suic._2021.days.day04;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    private int value;
    private boolean marked;

    public Cell(int value) {
        this.value = value;
    }
}