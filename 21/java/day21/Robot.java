package day21;

import java.util.*;

import static day21.Direction.*;
import static java.lang.Math.abs;
import static java.util.Collections.emptyList;

public record Robot(Pad pad, Vector pos) {
    public Robot(Pad pad) {
        this(pad, pad.map.get('A'));
    }

    static Map<Vector, List<Move>> cache = new HashMap<>();

    public Vector diff(Vector wanted) {
        return wanted.minus(pos);
    }

    public List<Move> moves(Vector d) {
        return cache.computeIfAbsent(d, diff -> {
            Direction x = diff.x() < 0 ? WEST : EAST;
            Direction y = diff.y() < 0 ? NORTH : SOUTH;
            return combinations(abs(diff.x()), x, abs(diff.y()), y);
        }).stream().filter(m -> !m.panics(this)).toList();
    }

    private List<Move> combinations(int x, Direction xDir, int y, Direction yDir) {
        List<Move> moves = new ArrayList<>();

        if (x > 0) {
            List<Move> combinations = combinations(x - 1, xDir, y, yDir);
            for (Move combination : combinations) {
                List<Direction> dirs = new ArrayList<>();
                dirs.add(xDir);
                dirs.addAll(combination.steps);
                moves.add(new Move(dirs));
            }
        }
        if (y > 0) {
            List<Move> combinations = combinations(x, xDir, y - 1, yDir);
            for (Move combination : combinations) {
                List<Direction> dirs = new ArrayList<>();
                dirs.add(yDir);
                dirs.addAll(combination.steps);
                moves.add(new Move(dirs));
            }
        }
        if (x == 0 && y == 0) {
            return List.of(new Move(emptyList()));
        }
        return moves;
    }

    public Robot move(Vector wanted) {
        return new Robot(pad, wanted);
    }


    static final class Move extends AbstractList<Character> {
        private final List<Direction> steps;

        Move(List<Direction> steps) {
            this.steps = steps;
        }

        @Override public int size() {
            return steps.size()+1;
        }

        @Override public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Direction step : steps) {
                    sb.append(step.toChar());
                }
                sb.append('A');
                return sb.toString();
            }

            public boolean panics(Robot r) {
                Vector pos = r.pos;
                for (Direction step : steps) {
                    pos = pos.add(step.v);
                    if (pos.equals(r.pad.panic)) {
                        return true;
                    }
                }
                ;
                return false;
            }

        public Collection<Direction> steps() {
            return steps;
        }

        @Override public Character get(int index) {
            return index == steps.size() ? 'A' : steps.get(index).toChar();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Move) obj;
            return Objects.equals(this.steps, that.steps);
        }

        @Override
        public int hashCode() {
            return Objects.hash(steps);
        }

        }

}
