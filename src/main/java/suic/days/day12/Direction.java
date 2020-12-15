package suic.days.day12;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum Direction {
    NORTH(new Point(0, 1)),
    EAST(new Point(1, 0)),
    SOUTH(new Point(0, -1)),
    WEST(new Point(-1, 0));

    private final Point offset;

    public static Direction rotate(Direction dir, int amount, boolean clockwise) {
        Direction result = dir;
        for (int i = 0; i < amount; i++) {
            result = switch (result) {
                case NORTH -> clockwise ? EAST : WEST;
                case EAST -> clockwise ? SOUTH : NORTH;
                case SOUTH -> clockwise ? WEST : EAST;
                case WEST -> clockwise ? NORTH : SOUTH;
            };
        }
        return result;
    }

}