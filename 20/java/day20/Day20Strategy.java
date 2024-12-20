package day20;

import day20.astar.Edge;
import day20.astar.Strategy;
import day20.astar.Visit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static day20.Vector.v;
import static java.lang.Math.abs;

public class Day20Strategy implements Strategy<Racer> {
    private final Grid grid;
    private final boolean cheat;

    public Day20Strategy(Grid grid, boolean cheat) {
        this.grid = grid;
        this.cheat = cheat;
    }

    @Override
    public boolean isEnd(Visit<Racer> visit) {
        if (grid.end().equals(visit.value.pos())) {
            //System.out.println("Found end "+visit.accumulatedScore);
            return true;
        } else return false;
    }

    @Override public Collection<Edge<Racer>> outgoingEdges(Visit<Racer> visit) {
        List<Edge<Racer>> list = new ArrayList<>();
        for (Vector v : getAdjacent(visit)) {
            if (grid.contains(v)) {
                Racer racer = visit.value;
                if (grid.isEmpty(v)) {
                    list.add(new Edge<>(1, racer.moveToEmpty(v)));
                } else if (cheat && racer.cheatAvailable()) {
                    list.add(new Edge<>(1, racer.moveToWall(v)));
                }
            }
        }
        return list;
    }

    private static Collection<Vector> getAdjacent(Visit<Racer> visit) {
        Visit<Racer> parent = visit.parent;
        Racer racer = visit.value;
        if (parent == null) {
            return List.of(
                    racer.pos().add(v(0, -1)),
                    racer.pos().add(v(1, 0)),
                    racer.pos().add(v(0, 1)),
                    racer.pos().add(v(-1, 0)));
        }
        Vector direction = racer.pos().minus(parent.value.pos());
        return List.of(
                racer.pos().add(direction),
                racer.pos().add(direction.turnLeft()),
                racer.pos().add(direction.turnRight()));
    }

    @Override public int heuristic(Racer value) {
        Vector diff = grid.end().minus(value.pos());
        return abs(diff.x()) + abs(diff.y());
    }
}
