package day20;

import java.util.Comparator;

public record Vector(int x, int y) implements Comparable<Vector> {

    public static final Comparator<Vector> COMPARATOR = Comparator.<Vector>comparingInt(v -> v.y).thenComparingInt(v -> v.x);

    static Vector v(int x, int y) {
        return new Vector(x,y);
    }

    Vector add(Vector other) {
        return v(x+other.x, y+other.y);
    }

    @Override
    public String toString() {
        return "("+x+" "+y+")";
    }

    @Override
    public int compareTo(Vector o) {
        return COMPARATOR.compare(this, o);
    }

    public Vector turnRight() {
        return v(-y, x);
    }

    public Vector turnLeft() {
        return v(y, -x);
    }

    public Vector minus(Vector other) {
        return v(x-other.x, y-other.y);
    }

}
