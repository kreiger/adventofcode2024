package day12;

import static day12.Vector.v;

public enum Direction {
    NORTH(v(0, -1)),
    EAST(v(1, 0)),
    SOUTH(v(0, 1)),
    WEST(v(-1, 0));

    final Vector v;

    Direction(Vector v) {
        this.v = v;
    }

    Direction turnLeft() {
        Direction[] values = values();
        return values[(ordinal() + 3) % values.length];
    }
    Direction turnRight() {
        Direction[] values = values();
        return values[(ordinal() + 1) % values.length];
    }
}
