package day16;

import java.util.Objects;

public final class Reindeer {
    public final Vector pos;
    public final Vector dir;

    public Reindeer(Vector pos, Vector dir) {
        this.pos = pos;
        this.dir = dir;
    }

    public Vector pos() {
        return pos;
    }

    public Vector dir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Reindeer) obj;
        return Objects.equals(this.pos, that.pos) &&
                Objects.equals(this.dir, that.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, dir);
    }

    @Override
    public String toString() {
        return "Reindeer[" +
                "pos=" + pos + ", " +
                "dir=" + dir + ']';
    }

}
