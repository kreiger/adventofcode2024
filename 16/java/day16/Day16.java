package day16;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day16 {
    private final Grid grid;

    public Day16(Grid grid) {
        this.grid = grid;
    }

    public static void main(String[] args) throws IOException {
        Grid grid = new Grid(Path.of("16/" + args[0]));
        new Day16(grid).run();

    }

    private void run() {
        System.out.println(grid);
        Reindeer start = new Reindeer(grid.start, new Vector(1, 0));
        Set<Vector> bestPathTiles = new HashSet<>();
        Visit<Reindeer> path = aStarAlgorithm(start, new Day16Strategy(grid, bestPathTiles));
        System.out.println(path);
        System.out.println(bestPathTiles.size());
    }

    private <T> Visit<T> aStarAlgorithm(T start, Strategy<T> strategy) {
        Visit<T> startNode = new Visit<>(0, start, null);
        PriorityQueue<Visit<T>> queue = new PriorityQueue<>(List.of(startNode));
        Map<T, Integer> gScore = new HashMap<>();
        while  (!queue.isEmpty()) {
            Visit<T> visit = queue.poll();
            if (strategy.isEnd(visit)) {
                return visit;
            }
            int currentGScore = visit.accumulatedScore;

            for (Edge<T> edge : strategy.outgoingEdges(visit.value)) {
                int tentativeGScore = currentGScore + edge.cost();
                if (tentativeGScore <= gScore.getOrDefault(edge.value(), Integer.MAX_VALUE)) {
                    Visit<T> childVisit = new Visit<>(tentativeGScore, edge.value(), visit);
                    gScore.put(edge.value(), tentativeGScore);
                    queue.add(childVisit);
                }
            }
        }


        return null;
    }

}
