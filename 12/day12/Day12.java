package day12;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
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
            for (Plot plot : region.plots.values()) {
                for (Direction dir : plot.sides.keySet()) {
                    buildSides(region, plot, dir, null);
                }
            }
            System.out.println(region);
            part1 += region.area * region.perimeter;
            part2 += region.area * region.sides.size();
        }
        System.out.println(part1);
        System.out.println(part2);
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

    private void buildSides(Region region, Plot plot, Direction dir, Side side) {
        if (!plot.sides.containsKey(dir)) return;
        if (plot.sides.get(dir) != null) return;
        if (side == null) side = newSide(region, dir);
        plot.sides.put(dir, side);
        side.positions().add(plot.position());
        Direction left = dir.turnLeft();
        Direction right = dir.turnRight();
        for (Direction d : new Direction[]{left, right}) {
            Plot neighbour = region.plots.get(plot.position().add(d.v));
            if (neighbour != null) {
                buildSides(region, neighbour, dir, side);
            }
        }
    }

    private Side newSide(Region region, Direction dir) {
        Side side = new Side(dir, new HashSet<>());
        region.sides.add(side);
        return side;
    }

}
