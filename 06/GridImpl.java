import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;

public class GridImpl implements Grid {
    int width;
    int height;
    Guard guard;
    private HashSet<Coord> obstructions = new HashSet<>();

    GridImpl(String path) throws IOException {
        BufferedReader br = Files.newBufferedReader(new File("06/"+path).toPath());
        // Read lines
        int y = 0;
        for (String line; null != (line = br.readLine()); y++) {
            int x = 0;
            for (; x < line.length(); x++) {
                char c = line.charAt(x);
                switch (c) {
                    case '^':
                        guard = new Guard(new Coord(x, y), Direction.UP);
                        break;
                    case '#':
                        obstructions.add(new Coord(x, y));
                }
                if (y == 0) width = x + 1;
            }
        }
        this.height = y;
    }

    @Override
    public boolean hasObstruction(Coord coord) {
        return obstructions.contains(coord);
    }

    @Override
    public boolean contains(Coord c) {
        return c.inside(width, height);
    }

    @Override public void forEach(Consumer<Coord> coordConsumer) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Coord c = new Coord(x, y);
                coordConsumer.accept(c);
            }
        }
    }

}
