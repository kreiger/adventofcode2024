package day16;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Day16Strategy implements Strategy<Reindeer> {
    private Grid grid;
    private final Set<Vector> bestPathTiles;
    private Integer bestScore = null;

    public Day16Strategy(Grid grid, Set<Vector> bestPathTiles) {
        this.grid = grid;
        this.bestPathTiles = bestPathTiles;
    }

    @Override public Collection<Edge<Reindeer>> outgoingEdges(Reindeer reindeer) {

        List<Edge<Reindeer>> edges = new ArrayList<>();
        Vector forward = reindeer.pos.add(reindeer.dir);
        if (grid.isEmpty(forward)) {
            edges.add(new Edge<>(1, new Reindeer(forward, reindeer.dir)));
        }
        Vector right = reindeer.dir.turnRight();
        if (grid.isEmpty(reindeer.pos.add(right))) {
            edges.add(new Edge<>(1000, new Reindeer(reindeer.pos, right)));
        }
        Vector left = reindeer.dir.turnLeft();
        if (grid.isEmpty(reindeer.pos.add(left))) {
            edges.add(new Edge<>(1000, new Reindeer(reindeer.pos, left)));
        }
        return edges;
    }

    @Override public int heuristic(Reindeer reindeer) {
        Vector diff = grid.end.minus(reindeer.pos);
        int d = Math.abs(diff.x()) + Math.abs(diff.y());
        if (diff.x() != 0 && diff.y() != 0) d+=1000;
        if (reindeer.dir.x() == -1 || reindeer.dir.y() == 1) d+=1000;
        return d;
    }

    @Override public boolean isEnd(Visit<Reindeer> visit) {
        boolean end = grid.end.equals(visit.value.pos);
        if (!end) {
            return false;
        }
        if (bestScore != null && visit.accumulatedScore != bestScore) {
            return true;
        }
        bestScore = visit.accumulatedScore;
        System.out.println("Found end " + visit.accumulatedScore);
        for (Visit<Reindeer> v = visit; v != null; v = v.parent) {
            bestPathTiles.add(v.value.pos);
        }
        return false;
    }

}
