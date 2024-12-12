package day12;

import java.io.File;
import java.io.IOException;

public class Day12 {
    public static void main(String[] args) throws IOException {
        GridImpl grid = new GridImpl(new File("12/" + args[0]).toPath());

        grid.forEach((plot) -> {
            Region region = plot.getRegion();
            if (region == null) {
                System.out.println("Part 1: " + grid.countRegion(plot));
            }

        });
    }

}
