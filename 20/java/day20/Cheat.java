package day20;

public record Cheat(int length, Vector start, Vector end) {

    public Cheat increment(Vector v) {
        if (available()) {
            return new Cheat(length + 1, start, v);
        }
        return this;
    }

    boolean available() {
        return length < 20;
    }

    public boolean contains(Vector v) {
        Vector diff = end.minus(start);
        Vector diff2 = v.minus(start);
    }
}
