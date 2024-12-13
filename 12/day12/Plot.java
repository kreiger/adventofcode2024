package day12;

import java.util.EnumMap;

class Plot {
    private final GridImpl grid;
    private final Vector position;
    private final char plant;
    private Region region;
    EnumMap<Direction, Side> sides = new EnumMap<>(Direction.class);

    public Plot(GridImpl grid, Vector position, char plant) {
        this.grid = grid;
        this.position = position;
        this.plant = plant;
    }

    public char plant() {
        return plant;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean samePlant(Vector v) {
        Plot plot = get(v);
        return plot != null && plot.plant() == plant;
    }

    public Plot get(Vector v) {
        return grid.get(position.add(v));
    }

    public Vector position() {
        return position;
    }

    @Override public String toString() {
        return String.valueOf(plant)+" "+position;
    }
}
