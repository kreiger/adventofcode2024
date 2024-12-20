package day20;

import java.util.Objects;

public final class Racer {
    private final Vector pos;
    private final int moves;
    private final Cheat cheat;

    public Racer(Vector pos, int moves, Cheat cheat) {
        this.pos = pos;
        this.moves = moves;
        this.cheat = cheat;
    }

    public Racer moveToEmpty(int distance, Vector v) {
        return new Racer(v, moves + distance, cheat);
    }

    public Racer cheat(int d, Vector v) {
        return new Racer(v,
                moves + d,
                new Cheat(pos, v));
    }

    public boolean cheatAvailable() {
        return cheat == null;
    }

    public Vector pos() {
        return pos;
    }

    public Cheat cheat() {
        return cheat;
    }

    public int moves() {
        return moves;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Racer) obj;
        return Objects.equals(this.pos, that.pos) &&
                Objects.equals(this.cheat, that.cheat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, cheat);
    }

    @Override
    public String toString() {
        return "Racer[" +
                "pos=" + pos + ", " +
                "moves=" + moves + ", " +
                "cheat=" + cheat + ']';
    }
}
