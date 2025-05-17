package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static String diff(String text1, String text2) {
        String[] left = text1.split("\n");
        String[] right = text2.split("\n");

        List<String> diff = new ArrayList<>();
        int n = Math.max(left.length, right.length);

        for (int i = 0; i < n; i++) {
            String left_line = i < left.length ? left[i] : "<N/D>";
            String right_line = i < right.length ? right[i] : "<N/D>";

            if (!left_line.equals(right_line)) {
                diff.add("Difference found at line " + (i + 1) + ":");
                diff.add("  -  " + left_line);
                diff.add("  +  " + right_line);
            }
        }

        return String.join("\n", diff);
    }
}
