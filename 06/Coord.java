public record Coord(int x, int y) {

    public boolean inside(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Coord move(Direction dir) {
        return new Coord(x + dir.x, y + dir.y);
    }

    public boolean inside(Grid grid) {
        return grid.contains(this);
    }
}
