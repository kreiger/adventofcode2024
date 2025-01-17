public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    final int x;
    final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Direction turnRight() {
        Direction[] values = values();
        return values[(ordinal() + 1) % values.length];
    }
}
