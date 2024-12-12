package day12;

import java.util.Objects;

import static day12.Direction.directions;
import static java.util.stream.Collectors.toList;

class PlotImpl implements Grid.Plot {
    private final GridImpl grid;
    private final int finalX;
    private final int finalY;
    private final char plant;
    private Region region;

    public PlotImpl(GridImpl grid, int finalX, int finalY, char plant) {
        this.grid = grid;
        this.finalX = finalX;
        this.finalY = finalY;
        this.plant = plant;
    }

    @Override public Iterable<Grid.Plot> neighbours() {
        return directions()
                .map(dir -> new Grid.Pos(finalX + dir.x, finalY + dir.y))
                .map(grid::get)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    @Override public char plant() {
        return plant;
    }

    @Override public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override public String toString() {
        return String.valueOf(plant);
    }
}
