package day20;

import day20.astar.Visit;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class GridImpl implements Grid {
    int width;
    int height;
    Vector start;
    Vector end;
    private Set<Vector> walls = new HashSet<>();

    GridImpl(Path path) throws IOException {
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

    @Override
    public String toString() {
        return toString(null);
    }

    public String toString(Visit<Racer> visits) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vector v = new Vector(x, y);
                char c;
                if (v.equals(start)) {
                    c = 'S';
                } else if (v.equals(end)) {
                    c = 'E';
                } else if (walls.contains(v)) {
                    c = '#';
                } else {
                    c = '.';
                }

                sb.append(c);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean isWall(Vector pos) {
        return walls.contains(pos);
    }

    @Override
    public boolean isEmpty(Vector pos) {
        return !isWall(pos);
    }

    @Override
    public boolean contains(Vector v) {
        return v.x() >= 0 && v.x() < width && v.y() >= 0 && v.y() < height;
    }

    @Override
    public Vector end() {
        return end;
    }


}
