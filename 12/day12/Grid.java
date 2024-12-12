package day12;

import java.util.function.Consumer;

public interface Grid {
    void forEach(Consumer<Plot> consumer);

    int getWidth();
    int getHeight();

    Plot get(Pos pos);

    record Pos(int x, int y) {

    }

    interface Plot {

        Iterable<Plot> neighbours();

        char plant();

        Region getRegion();

        void setRegion(Region region);
    }
}
