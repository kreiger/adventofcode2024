package day20.astar;

import java.util.*;
import java.util.function.Predicate;

public class AStar {

    public <T> Visit<T> shortest(T start, Strategy<T> strategy) {
        return aStar(start, 0, strategy, end -> false);
    }

    public <T> List<Visit<T>> allShortest(T start, Strategy<T> strategy) {
        List<Visit<T>> allShortest = new ArrayList<>();
        aStar(start, 0, strategy, new Predicate<>() {
            private int minScore = Integer.MAX_VALUE;

            @Override
            public boolean test(Visit<T> visit) {
                if (visit.accumulatedScore > minScore) {
                    return false;
                }
                minScore = visit.accumulatedScore;
                allShortest.add(visit);
                return true;
            }
        });
        return allShortest;
    }

    public <T> NavigableMap<Integer, List<Visit<T>>> shorterThan(T start, Strategy<T> strategy, int maxScore) {
        NavigableMap<Integer, List<Visit<T>>> shorterThan = new TreeMap<>();
        aStar(start, 0, strategy, end -> {
            if (end.accumulatedScore >= maxScore) {
                return false;
            }
            shorterThan.computeIfAbsent(end.accumulatedScore, k -> new ArrayList<>()).add(end);
            return true;
        });
        return shorterThan;
    }

    public <T> NavigableMap<Integer, List<Visit<T>>> all(T start, Strategy<T> strategy) {
        NavigableMap<Integer, List<Visit<T>>> results = new TreeMap<>();
        aStar(start, 0, strategy, end -> {
            results.computeIfAbsent(end.accumulatedScore, k -> new ArrayList<>()).add(end);
            return true;
        });
        return results;
    }

    public <T> Visit<T> aStar(T start, int startScore, Strategy<T> strategy, Predicate<Visit<T>> keepSearching) {
        Visit<T> startNode = new Visit<>(startScore, start, null);
        PriorityQueue<Visit<T>> queue = new PriorityQueue<>(List.of(startNode));
        Set<T> visited = new HashSet<>();
        Map<T, Integer> gScore = new HashMap<>();
        while  (!queue.isEmpty()) {
            Visit<T> visit = queue.poll();
            if (strategy.isEnd(visit)) {
                if (keepSearching.test(visit)) {
                    continue;
                }
                return visit;
            }
            visited.add(visit.value);
            int currentGScore = visit.accumulatedScore;

            Collection<Edge<T>> edges = strategy.outgoingEdges(visit, visited);
            for (Edge<T> edge : edges) {
                int tentativeGScore = currentGScore + edge.cost();
                if (tentativeGScore < gScore.getOrDefault(edge.value(), Integer.MAX_VALUE) && !visited.contains(edge.value())) {
                    Visit<T> childVisit = new Visit<>(tentativeGScore, edge.value(), visit);
                    gScore.put(edge.value(), tentativeGScore);
                    queue.add(childVisit);
                }
            }
        }

        return null;
    }

}
