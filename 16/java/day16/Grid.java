package day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Grid {
    int width;
    int height;
    Vector start;
    Vector end;
    private Set<Vector> walls = new HashSet<>();

    Grid(Path path) throws IOException {
        BufferedReader br = Files.newBufferedReader(path);
        int y = 0;
        for (String line; null != (line = br.readLine()); y++) {
            int x = 0;
            for (; x < line.length(); x++) {
                char c = line.charAt(x);
                Vector pos = new Vector(x, y);
                switch (c) {
                    case '#' -> {
                        walls.add(pos);
                    }
                    case 'S' -> {
                        start = pos;
                    }
                    case 'E' -> {
                        end = pos;
                    }
                }
            }
            if (y == 0) width = x;
        }
        this.height = y;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(walls.contains(new Vector(x, y)) ? '#' : '.');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public boolean isWall(Vector pos) {
        return walls.contains(pos);
    }

    public boolean isEmpty(Vector pos) {
        return !isWall(pos);
    }

}
