package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility class providing text manipulation and comparison operations.
 */
public class TextUtils {
    /**
     * Compares two texts line by line and generates a report of their differences.
     *
     * @param text1 The first text to compare
     * @param text2 The second text to compare
     * @return A string containing the differences between the texts, with each difference
     * showing the line number and the corresponding lines from both texts.
     * Returns an empty string if no differences are found.
     */
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
