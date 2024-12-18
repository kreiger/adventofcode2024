package day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    Vector end;
    int width;
    int height;
    final Map<Vector, Integer> corruptedTimestamps = new HashMap<>();
    final List<Vector> timestampsCorrupted = new ArrayList<>();

    Grid(Path path) throws IOException {
        BufferedReader br = Files.newBufferedReader(path);
        int timestamp = 0;
        for (String line; null != (line = br.readLine()); timestamp++) {
            String[] split = line.split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            Vector vector = new Vector(x, y);
            if (corruptedTimestamps.containsKey(vector)) {
                throw new IllegalArgumentException("Duplicate vector: " + vector);
            }
            corruptedTimestamps.put(vector, timestamp);
            timestampsCorrupted.add(vector);
            if (width <= x) width = x + 1;
            if (height <= y) height = y + 1;
        }
        end = new Vector(width-1, height-1);
        System.out.println("width: " + width + ", height: " + height);
    }

    public boolean isWall(Vector pos, int timestamp) {

        return corruptedTimestamps.getOrDefault(pos, Integer.MAX_VALUE) <= timestamp;
    }

    public boolean isEmpty(Vector pos, int timestamp) {
        return !isWall(pos, timestamp);
    }

    public String toString(int timestamp) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vector pos = new Vector(x, y);
                if (isWall(pos, timestamp)) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean contains(Vector v) {
        return v.x() >= 0 && v.x() < width && v.y() >= 0 && v.y() < height;
    }
}
