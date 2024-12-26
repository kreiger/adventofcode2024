package day21;

import java.util.HashMap;
import java.util.Map;

public class Pad {
    private final String pad;
    Map<Character, Vector> map = new HashMap<>();
    Vector panic;

    public Pad(String pad) {
        this.pad = pad;
        int x = 0,y = 0;
        for (int i = 0; i < pad.length(); i++) {
            char c = pad.charAt(i);
            switch (c) {
                case '\n' -> {
                    x = 0;
                    y++;
                    continue;
                }
                case ' ' -> { panic = new Vector(x, y); }
                default -> map.put(c, new Vector(x, y));
            }
            x++;
        }
    }

    @Override public String toString() {
        return pad;
    }
}
