package suic.days.day11;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CellState {
    FLOOR('.'),
    EMPTY('L'),
    FILLED('#');

    char character;

    public static CellState parseState(char character) {
        for (CellState state : CellState.values()) {
            if (state.character == character) {
                return state;
            }
        }
        throw new IllegalArgumentException();
    }
}
