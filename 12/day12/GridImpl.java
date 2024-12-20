package day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.Consumer;

public class GridImpl implements Grid {
    int width;
    int height;
    private HashMap<Vector, Plot> plants = new HashMap<>();

    GridImpl(Path path) throws IOException {
        BufferedReader br = Files.newBufferedReader(path);
        int y = 0;
        for (String line; null != (line = br.readLine()); y++) {
            int x = 0;
            for (; x < line.length(); x++) {
                char plant = line.charAt(x);
                Vector pos = new Vector(x, y);
                plants.put(pos, new Plot(this, pos, plant));
            }
            if (y == 0) width = x;
        }
        this.height = y;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(plants.get(new Vector(x, y)).plant());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override public void forEach(Consumer<Plot> consumer) {
        plants.forEach((pos, plot) -> consumer.accept(plot));
    }

    @Override
    public Plot get(Vector pos) {
        return plants.get(pos);
    }
}
