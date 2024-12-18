package day18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar {

    public <T> Visit<T> aStar(T start, Strategy<T> strategy) {
        Visit<T> startNode = new Visit<>(0, start, null);
        PriorityQueue<Visit<T>> queue = new PriorityQueue<>(List.of(startNode));
        Map<T, Integer> gScore = new HashMap<>();
        while  (!queue.isEmpty()) {
            Visit<T> visit = queue.poll();
            //System.out.println(visit);
            if (strategy.isEnd(visit)) {
                return visit;
            }
            int currentGScore = visit.accumulatedScore;

            for (Edge<T> edge : strategy.outgoingEdges(visit)) {
                int tentativeGScore = currentGScore + edge.cost();
                if (tentativeGScore < gScore.getOrDefault(edge.value(), Integer.MAX_VALUE)) {
                    Visit<T> childVisit = new Visit<>(tentativeGScore, edge.value(), visit);
                    gScore.put(edge.value(), tentativeGScore);
                    queue.add(childVisit);
                }
            }
        }


        return null;
    }

}
