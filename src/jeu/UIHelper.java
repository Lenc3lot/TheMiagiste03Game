package jeu;

public class UIHelper {
    public static final int WIDTH = 60;

    public static String line() {
        return "+" + "-".repeat(WIDTH - 2) + "+\n";
    }

    public static String center(String text) {
        int padding = (WIDTH - 2 - text.length()) / 2;
        int extra = (WIDTH - 2 - text.length()) % 2;
        return "|" + " ".repeat(padding) + text + " ".repeat(padding + extra) + "|\n";
    }

    public static String left(String text) {
        StringBuilder sb = new StringBuilder();
        int maxLen = WIDTH - 2;
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + maxLen, text.length());
            String part = text.substring(start, end);
            sb.append("|").append(part);
            sb.append(" ".repeat(maxLen - part.length())).append("|\n");
            start = end;
        }
        if (text.isEmpty()) {
            sb.append("|").append(" ".repeat(maxLen)).append("|\n");
        }
        return sb.toString();
    }

    public static String box(String text) {
        String[] lines = text.split("\r?\n");
        StringBuilder sb = new StringBuilder();
        sb.append(line());
        for (String line : lines) {
            sb.append(left(line));
        }
        sb.append(line());
        return sb.toString();
    }
} 