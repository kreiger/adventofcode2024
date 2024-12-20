package day20;

public class CheatGrid implements Grid {
    private final Grid grid;
    private final Vector cheatPos;

    CheatGrid(Grid grid, Vector cheatPos) {
        super();
        this.grid = grid;
        this.cheatPos = cheatPos;
    }

    @Override
    public boolean isWall(Vector pos) {
        return !cheatPos.equals(pos) && grid.isWall(pos);
    }

    @Override
    public boolean isEmpty(Vector pos) {
        return !isWall(pos);
    }

    @Override
    public boolean contains(Vector v) {
        return grid.contains(v);
    }

    @Override
    public Vector end() {
        return grid.end();
    }
}
