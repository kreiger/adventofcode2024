package day20;

import day20.astar.AStar;
import day20.astar.Visit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day20Part2 {

    private final int threshold;
    private final GridImpl grid;

    public Day20Part2(int threshold, GridImpl grid) {
        this.threshold = threshold;
        this.grid = grid;
    }

    public static void main(String[] args) throws IOException {
        int threshold = Integer.parseInt(args[0]);
        GridImpl grid = new GridImpl(Path.of("20/" + args[1]));
        System.out.println(grid);
        new Day20Part2(threshold, grid).part2();

    }

    private void part2() {
        Racer start = new Racer(grid.start, 0, null);
        AStar aStar = new AStar();
        Visit<Racer> shortest = aStar.shortest(start, new Day20Strategy(grid));
        int best = shortest.value.moves();
        Map<Vector, Integer> moves = new HashMap<>();
        for (Visit<Racer> v = shortest; v != null; v = v.parent) {
            moves.put(v.value.pos(), v.value.moves());
        }
        SortedMap<Integer, Set<Cheat>> savedCount = new TreeMap<>();
        for (Visit<Racer> v = shortest; v != null; v = v.parent) {
            for (Vector diamond : Day20Strategy.DIAMOND) {
                Vector cheat = v.value.pos().plus(diamond);
                Integer cheatMoves = moves.get(cheat);
                if (null != cheatMoves) {
                    int saved = cheatMoves - v.value.moves() - diamond.manhattan();
                    if (saved >= threshold) {
                        savedCount.computeIfAbsent(saved, k -> new HashSet<>()).add(new Cheat(v.value.pos(), cheat));
                    }
                }
            }
        }

        System.out.println("Best: " + best);
        int total = 0;
        for (Integer saved : savedCount.keySet()) {
            int count = savedCount.get(saved).size();
            System.out.println("There are "+ count +" cheats that save "+saved+" picoseconds");
            if (saved >= threshold) {
                total += count;
            }
        }
        System.out.println("Total: " + total);
    }

}
