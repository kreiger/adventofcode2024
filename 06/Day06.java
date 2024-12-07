import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

public class Day06 {
    public static void main(String[] args) throws IOException {
        GridImpl grid = new GridImpl(args[0]);
        Set<Coord> visited = new HashSet<>();
        grid.guard.travel(grid, guard -> {
            visited.add(guard.coord());
            return true;
        });
        System.out.println(visited.size());

        Set<Coord> newObstructions = new HashSet<>();
        grid.forEach((Coord c) -> {
            Set<Guard> loopVisited = new HashSet<>();
            if (grid.guard.coord().equals(c)) {
                return;
            }
            grid.guard.travel(grid.plus(c), guard -> {
                if (!loopVisited.add(guard)) {
                    newObstructions.add(c);
                    return false;
                }
                return true;
            });
        });
        System.out.println(newObstructions.size());
    }

}
