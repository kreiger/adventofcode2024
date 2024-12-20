package day20;

import java.util.Objects;

public final class Cheat {
    private final Vector start;
    private final Vector end;

    public Cheat(Vector start, Vector end) {
        this.start = start;
        this.end = end;
    }

    public Vector start() {
        return start;
    }

    public Vector end() {
        return end;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Cheat) obj;
        return Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Cheat[" +
                "start=" + start + ", " +
                "end=" + end + ']';
    }

}
