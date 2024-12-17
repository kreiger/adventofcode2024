package day16;

import java.util.Collection;

public interface Strategy<T> {
    boolean isEnd(Visit<T> value);

    Collection<Edge<T>> outgoingEdges(T value);

    int heuristic(T value);

}
