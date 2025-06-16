package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    @Test
    @DisplayName("Should return empty string when texts are identical")
    void testDiff_IdenticalTexts_ShouldReturnEmptyString() {
        String text1 = "Hello";
        String text2 = "Hello";
        String result = TextUtils.diff(text1, text2);
        assertEquals("", result);
    }

    @Test
    @DisplayName("Should return empty string when both texts are empty")
    void shouldReturnEmptyStringWhenBothTextsAreEmpty() {
        String text1 = "";
        String text2 = "";
        String result = TextUtils.diff(text1, text2);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should detect difference in single line")
    void shouldDetectDifferenceInSingleLine() {
        String text1 = "Hello World";
        String text2 = "Hello Java";
        String result = TextUtils.diff(text1, text2);
        String expected = "Difference found at line 1:\n" +
                "  -  Hello World\n" +
                "  +  Hello Java";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should detect multiple differences")
    void shouldDetectMultipleDifferences() {
        String text1 = "line1\nline2\nline3";
        String text2 = "line1\nchanged\nline3";
        String result = TextUtils.diff(text1, text2);
        String expected = "Difference found at line 2:\n" +
                "  -  line2\n" +
                "  +  changed";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should handle first text longer than second")
    void shouldHandleFirstTextLongerThanSecond() {
        String text1 = "line1\nline2\nline3";
        String text2 = "line1";
        String result = TextUtils.diff(text1, text2);
        String expected = "Difference found at line 2:\n" +
                "  -  line2\n" +
                "  +  <N/D>\n" +
                "Difference found at line 3:\n" +
                "  -  line3\n" +
                "  +  <N/D>";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should handle second text longer than first")
    void shouldHandleSecondTextLongerThanFirst() {
        String text1 = "line1";
        String text2 = "line1\nline2\nline3";
        String result = TextUtils.diff(text1, text2);
        String expected = "Difference found at line 2:\n" +
                "  -  <N/D>\n" +
                "  +  line2\n" +
                "Difference found at line 3:\n" +
                "  -  <N/D>\n" +
                "  +  line3";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should handle null input gracefully")
    void shouldHandleNullInputGracefully() {
        String text1 = null;
        String text2 = "Hello";
        assertThrows(NullPointerException.class, () -> {
            TextUtils.diff(text1, text2);
        });

        String text3 = "Hello";
        String text4 = null;
        assertThrows(NullPointerException.class, () -> {
            TextUtils.diff(text3, text4);
        });
    }

    @Test
    @DisplayName("Should handle texts with only whitespace differences")
    void shouldHandleTextsWithWhitespaceDifferences() {
        String text1 = "Hello World";
        String text2 = "Hello  World";  // extra space
        String result = TextUtils.diff(text1, text2);
        String expected = "Difference found at line 1:\n" +
                "  -  Hello World\n" +
                "  +  Hello  World";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should handle multiline texts with consecutive differences")
    void shouldHandleConsecutiveDifferences() {
        String text1 = "line1\nline2\nline3\nline4";
        String text2 = "line1\nchanged2\nchanged3\nline4";
        String result = TextUtils.diff(text1, text2);
        String expected = "Difference found at line 2:\n" +
                "  -  line2\n" +
                "  +  changed2\n" +
                "Difference found at line 3:\n" +
                "  -  line3\n" +
                "  +  changed3";
        assertEquals(expected, result);
    }
}