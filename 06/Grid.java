import java.util.function.Consumer;

public interface Grid {
    boolean hasObstruction(Coord coord);

    boolean contains(Coord c);

    default Grid plus(Coord newObstruction) {
        if (!contains(newObstruction)) {
            throw new IllegalArgumentException("Obstruction outside grid");
        }

        return new GridWithExtraObstruction(this, newObstruction);
    }

    void forEach(Consumer<Coord> coordConsumer);

}
