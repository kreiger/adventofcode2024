package day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day21 {

    public static final Pad NUM_PAD = new Pad("""
            789
            456
            123
             0A
            """);
    public static final Pad DIR_PAD = new Pad("""
             ^A
            <v>""");
    private final List<String> codes;

    public Day21(List<String> codes) {
        this.codes = codes;
    }

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        List<String> codes = Files.readAllLines(Path.of("21", fileName))
                .stream().map(s -> s.substring(0, 4)).toList();
        System.out.println(codes);
        new Day21(codes).run();
    }

    private void run() {
        Robot r0 = new Robot(NUM_PAD);
        Robot r1 = new Robot(DIR_PAD);
        Robot r2 = new Robot(DIR_PAD);
        int total = 0;
        for (String c0 : codes) {
            System.out.println(c0);
            long len = code(c0, List.of(r0, r1, r2));
            int num = Integer.parseInt(c0.substring(0, 3));
            long complexity = len * num;
            System.out.println(String.format("%d * %d = %d", len, num, complexity));
            total+=complexity;
        }
        System.out.println(total);
    }

    private long code(String code, List<Robot> robots) {
        if (robots.isEmpty()) {
            return code.length();
        }

        Robot r = robots.get(0);
        long total = 0;
        for (char digit : code.toCharArray()) {
            Vector wanted = r.pad().map.get(digit);
            Vector d = r.diff(wanted);
            List<Robot.Move> moves = r.moves(d);
            Long shortestCode = null;
            for (Robot.Move move : moves) {
                if (move.panics(r)) continue;
                long moveCode = code(move.toString(), robots.subList(1, robots.size()));
                if (shortestCode == null || moveCode < shortestCode) {
                    shortestCode = moveCode;
                }
            }
            r = r.move(wanted);
            total += shortestCode;
        }
        return total;
    }

}
