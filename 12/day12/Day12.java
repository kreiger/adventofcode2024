package day12;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 {
    List<Region> regions = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        new Day12().run(new File("12/" + args[0]));
    }

    private void run(File file) throws IOException {
        GridImpl grid = new GridImpl(file.toPath());
        grid.forEach((plot) -> {
            visit(plot, null);
        });
        int total = 0;
        for (Region region : regions) {
            System.out.println(region);
            total += region.area * region.perimeter;
        }
        System.out.println(total);
    }

    private void visit(Grid.Plot plot, Region region) {
        if (plot.getRegion() != null) return;
        if (region == null) region = newRegion(plot);
        region.perimeter+=4;
        region.area++;
        plot.setRegion(region);
        for (Grid.Plot neighbour : plot.neighbours()) {
            if (neighbour.plant() != plot.plant()) continue;
            region.perimeter--;
            visit(neighbour, region);
        }
    }

    private Region newRegion(Grid.Plot plot) {
        Region region = new Region(plot.plant(), 0, 0);
        regions.add(region);
        return region;
    }

}
