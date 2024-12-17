package day16;

record Node<T>(int fScore, T value) implements Comparable<Node<T>> {
    @Override public int compareTo(Node<T> o) {
        return Integer.compare(fScore, o.fScore);
    }
}
