package day20;

public record Racer(Vector pos, Cheat cheat) {

    public Racer moveToEmpty(Vector v) {
        return new Racer(v, null == cheat ? null : cheat.increment(v));
    }

    public Racer moveToWall(Vector v) {
        return new Racer(v, null == cheat ? new Cheat(1, pos, v) : cheat.increment(v));
    }

    public boolean cheatAvailable() {
        return null == cheat || cheat.available();
    }

}
