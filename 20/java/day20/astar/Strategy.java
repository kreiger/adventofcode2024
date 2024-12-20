package day20.astar;

import java.util.Collection;
import java.util.Set;

public interface Strategy<T> {
    boolean isEnd(Visit<T> value);

    Collection<Edge<T>> outgoingEdges(Visit<T> value, Set<T> visited);

    int heuristic(T value);

}
