package day20.astar;

import java.util.*;
import java.util.function.Predicate;

public class AStar {

    public <T> Visit<T> shortest(T start, Strategy<T> strategy) {
        return aStar(start, strategy, end -> false);
    }

    public <T> List<Visit<T>> allShortest(T start, Strategy<T> strategy) {
        List<Visit<T>> allShortest = new ArrayList<>();
        aStar(start, strategy, new Predicate<>() {
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
        aStar(start, strategy, end -> {
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
        aStar(start, strategy, end -> {
            results.computeIfAbsent(end.accumulatedScore, k -> new ArrayList<>()).add(end);
            return true;
        });
        return results;
    }

    public <T> Visit<T> aStar(T start, Strategy<T> strategy, Predicate<Visit<T>> keepSearching) {
        Visit<T> startNode = new Visit<>(0, start, null);
        PriorityQueue<Visit<T>> queue = new PriorityQueue<>(List.of(startNode));
        Map<T, Integer> gScore = new HashMap<>();
        while  (!queue.isEmpty()) {
            Visit<T> visit = queue.poll();
            if (strategy.isEnd(visit)) {
                if (keepSearching.test(visit)) {
                    continue;
                }
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
