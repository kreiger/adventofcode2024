package day20;

import day20.astar.AStar;
import day20.astar.Visit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.SortedMap;
import java.util.TreeMap;

public class Day20 {

    private final GridImpl grid;

    public Day20(GridImpl grid) {
        this.grid = grid;
    }

    public static void main(String[] args) throws IOException {
        GridImpl grid = new GridImpl(Path.of("20/" + args[0]));
        System.out.println(grid);
        new Day20(grid).part1();

    }

    private void part1() {
        Racer start = new Racer(grid.start, null);
        AStar aStar = new AStar();
        Visit<Racer> shortest = aStar.shortest(start, new Day20Strategy(grid, false));
        int best = shortest.accumulatedScore;
        SortedMap<Integer, Integer> savedCount = new TreeMap<>();
        for (int y = 0; y < grid.height; y++) {
            if (y % 10 ==0 ) {
                System.out.println("Y: " + y);
            }
            for (int x = 0; x < grid.width; x++) {
                CheatGrid cheatGrid = new CheatGrid(grid, new Vector(x, y));
                Visit<Racer> cheated = aStar.shortest(start, new Day20Strategy(cheatGrid, false));
                int saved = best - cheated.accumulatedScore;
                if (saved > 0) {
                    savedCount.compute(saved, (k, v) -> v == null ? 1 : v + 1);
                }
            }
        }
        int total = 0;
        for (Integer saved : savedCount.keySet()) {
            Integer count = savedCount.get(saved);
            System.out.println("There are "+ count +" cheats that save "+saved+" picoseconds");
            if (saved >= 100) {
                total += count;
            }
        }
        System.out.println("Total: " + total);
    }

}
