package day12;

import java.util.HashMap;
import java.util.Map;

public final class Region {
    char plant;
    int perimeter;
    int area;
    Map<Vector, Plot> plots = new HashMap<>();

    public Region(char plant) {
        this.plant = plant;
    }

    @Override
    public String toString() {
        return "plant: "+ plant +", plots: "+plots.size()+", area: "+area+", perimeter: "+perimeter;
    }


    public Plot seek(Plot plot, Direction dir) {
        do {
            Plot next = plots.get(plot.position().add(dir.v));
            if (next == null) break;
            plot = next;
        } while (true);
        return plot;
    }
}
