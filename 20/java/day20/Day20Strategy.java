package day20;

import day20.astar.Edge;
import day20.astar.Strategy;
import day20.astar.Visit;

import java.util.*;

import static day20.Vector.v;

public class Day20Strategy implements Strategy<Racer> {
    public static final List<Vector> DIAMOND = diamond();
    private final Grid grid;

    public Day20Strategy(Grid grid) {
        this.grid = grid;
    }

    @Override
    public boolean isEnd(Visit<Racer> visit) {
        if (grid.end().equals(visit.value.pos())) {
            //System.out.println("Found end "+visit.accumulatedScore);
            return true;
        } else return false;
    }

    @Override public Collection<Edge<Racer>> outgoingEdges(Visit<Racer> visit, Set<Racer> visited) {
        List<Edge<Racer>> list = new ArrayList<>();
        Racer racer = visit.value;
        for (Vector v : getAdjacent(visit)) {
            if (grid.contains(v)) {
                boolean empty = grid.isEmpty(v);
                if (empty) {
                    list.add(new Edge<>(1, racer.moveToEmpty(1, v)));
                }
            }
        }

        return list;
    }

    static List<Vector> diamond() {
        List<Vector> diamond = new ArrayList<>();
        // Find all the empty squares within Manhattan distance 20
        for (int d = 1; d <= 20; d++) {
            for (int x = 0; x < d; x++) {
                int y = d - x;
                Vector n = v(x, -y);
                for (int dir = 0; dir < 4; dir++) {
                    diamond.add(n);
                    n = n.turnRight();
                }
            }
        }
        return diamond.reversed();
    }

    private static Collection<Vector> getAdjacent(Visit<Racer> visit) {
        Racer racer = visit.value;
        return List.of(
                racer.pos().add(v(0, -1)),
                racer.pos().add(v(1, 0)),
                racer.pos().add(v(0, 1)),
                racer.pos().add(v(-1, 0)));
    }

    @Override public int heuristic(Racer value) {
        return grid.end().minus(value.pos()).manhattan();
    }
}
