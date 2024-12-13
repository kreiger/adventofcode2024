package day12;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Day12 {
    List<Region> regions = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        new Day12().run(new File("12/" + args[0]));
    }

    private void run(File file) throws IOException {
        GridImpl grid = new GridImpl(file.toPath());
        grid.forEach((plot) -> {
            buildRegions(plot, null);

        });
        int part1 = 0;
        int part2 = 0;
        for (Region region : regions) {
            buildSides(region);
            System.out.println(region);
            part1 += region.area * region.perimeter;
        }
        System.out.println(part1);
        System.out.println(part2);
    }

    private void buildSides(Region region) {
        region.plots.values().forEach(plot -> {
            buildSides(region, plot);
        });
    }

    private void buildSides(Region region, Plot plot) {
        for (Direction sideDir : plot.sides.keySet()) {
            Side side = plot.sides.get(sideDir);
            if (side != null) continue;

            Direction left = sideDir.turnLeft();

            Plot sideStart = region.seek(plot, left);
            side = new Side(sideDir, sideStart.position());
            for (Plot p = sideStart; p != null; p = region.plots.get(p.position())) {

            }
        }
    }



    private void buildRegions(Plot plot, Region region) {
        if (plot.getRegion() != null) return;
        if (region == null) region = newRegion(plot);
        region.perimeter+=4;
        region.area++;
        region.plots.put(plot.position(), plot);
        plot.setRegion(region);
        EnumSet<Direction> neighbours = EnumSet.noneOf(Direction.class);
        for (Direction dir: Direction.values()) {
            if (plot.samePlant(dir.v)) {
                neighbours.add(dir);
            } else {
                plot.sides.put(dir, null);
            }
        }

        for (Direction dir : neighbours) {
            region.perimeter--;
            buildRegions(plot.get(dir.v), region);
        }
    }

    private Region newRegion(Plot plot) {
        Region region = new Region(plot.plant());
        regions.add(region);
        return region;
    }

}
