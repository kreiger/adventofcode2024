package day20;

public interface Grid {
    boolean isWall(Vector pos);

    boolean isEmpty(Vector pos);

    boolean contains(Vector v);

    Vector end();
}
