package day12;

import java.util.function.Consumer;

public interface Grid {
    void forEach(Consumer<Plot> consumer);

    Plot get(Vector pos);


}

