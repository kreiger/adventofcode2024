package day20.astar;

import java.util.Collection;

public interface Strategy<T> {
    boolean isEnd(Visit<T> value);

    Collection<Edge<T>> outgoingEdges(Visit<T> value);

    int heuristic(T value);

}
