import java.util.function.Predicate;

public record Guard(Coord coord, Direction dir) {

    Guard move() {
        Coord move = coord().move(dir());
        return new Guard(move, dir);
    }

    public Guard turnRight() {
        return new Guard(coord, dir.turnRight());
    }

    void travel(Grid grid, Predicate<Guard> predicate) {
        for (Guard g = this; grid.contains(g.coord()); g = g.move()) {
            if (!predicate.test(g)) return;
            while (grid.hasObstruction(g.move().coord())) {
                g = g.turnRight();
            }
        }
    }
}
