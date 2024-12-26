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
            String c3 = code(c0, List.of(r0, r1, r2));
            System.out.println(c3);
            int len = c3.length();
            int num = Integer.parseInt(c0.substring(0, 3));
            int complexity = len * num;
            System.out.println(String.format("%d * %d = %d", len, num, complexity));
            total+=complexity;
        }
        System.out.println(total);
    }

    private String code(String code, List<Robot> robots) {
        if (robots.isEmpty()) {
            return code;
        }

        Robot r = robots.get(0);
        StringBuilder sb = new StringBuilder();
        for (char digit : code.toCharArray()) {
            Vector wanted = r.pad().map.get(digit);
            List<Robot.Move> moves = r.moves(wanted);
            String shortestCode = null;
            for (Robot.Move move : moves) {
                if (move.panics(r)) continue;
                String moveCode = code(move.toString(), robots.subList(1, robots.size()));
                if (shortestCode == null || moveCode.length() < shortestCode.length()) {
                    shortestCode = moveCode;
                }
            }
            r = r.move(wanted);
            sb.append(shortestCode);
        }
        return sb.toString();
    }

}
