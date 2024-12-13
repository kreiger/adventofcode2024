package day12;

import java.util.Comparator;

public record Vector(int x, int y) implements Comparable<Vector> {

    public static final Comparator<Vector> COMPARATOR = Comparator.<Vector>comparingInt(v -> v.y).thenComparingInt(v -> v.x);

    static Vector v(int x, int y) {
        return new Vector(x,y);
    }

    Vector add(Vector other) {
        return v(x+other.x, y+other.y);
    }

    Vector half() {
        return v(x>>1, y>>1);
    }

    @Override
    public String toString() {
        return "("+x+" "+y+")";
    }

    @Override
    public int compareTo(Vector o) {
        return COMPARATOR.compare(this, o);
    }
}
