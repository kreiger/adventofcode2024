package day18;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class Day18 {
    private final Grid grid;
    private final int timestamp;

    public Day18(Grid grid, int timestamp) {
        this.grid = grid;
        this.timestamp = timestamp;
    }

    public static void main(String[] args) throws IOException {
        Grid grid = new Grid(Path.of("18/" + args[0]));
        int timestamp = args.length == 2 ? Integer.parseInt(args[1]) : 0;
        new Day18(grid, timestamp).run();

    }

    private void run() {
        System.out.println(grid.toString(timestamp));
        Vector start = new Vector(0,0);
        Visit<Vector> path = new AStar().aStar(start, new Day18Strategy(grid, timestamp));
        System.out.println(path.accumulatedScore);
    }


    private class Day18Strategy implements Strategy<Vector> {
        private final Grid grid;
        private final Vector end;
        int timestamp;

        public Day18Strategy(Grid grid, int timestamp) {
            this.grid = grid;
            this.end = new Vector(grid.width-1, grid.height-1);
            this.timestamp = timestamp;
        }

        @Override public boolean isEnd(Visit<Vector> value) {
            return value.value.equals(end);
        }

        @Override public Collection<Edge<Vector>> outgoingEdges(Visit<Vector> visit) {
            return getAdjacent(visit).stream()
                    .filter(grid::contains)
                    .filter(v -> grid.isEmpty(v, timestamp))
                    .map(v -> new Edge<>(1, v))
                    .toList();
        }

        private static Collection<Vector> getAdjacent(Visit<Vector> visit) {
            Visit<Vector> parent = visit.parent;
            if (parent == null) {
                return List.of(
                        visit.value.add(Vector.v(0, 1)),
                        visit.value.add(Vector.v(1, 0)));
            }
            Vector direction = visit.value.minus(parent.value);
            return List.of(
                    visit.value.add(direction),
                    visit.value.add(direction.turnLeft()),
                    visit.value.add(direction.turnRight()));
        }

        @Override public int heuristic(Vector value) {
            Vector diff = grid.end.minus(value);
            return diff.x() + diff.y();
        }
    }
}
