class GridWithExtraObstruction extends GridDecorator {
    private final Coord newObstruction;

    public GridWithExtraObstruction(Grid grid, Coord newObstruction) {
        super(grid);
        this.newObstruction = newObstruction;
    }

    @Override
    public boolean hasObstruction(Coord coord) {
        return coord.equals(newObstruction) || super.hasObstruction(coord);
    }
}
