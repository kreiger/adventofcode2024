public class GridDecorator implements Grid {
    private final Grid grid;

    public GridDecorator(Grid grid) {
        this.grid = grid;
    }


    @Override
    public boolean hasObstruction(Coord coord) {
        return grid.hasObstruction(coord);
    }

    @Override
    public boolean contains(Coord c) {
        return grid.contains(c);
    }

}
