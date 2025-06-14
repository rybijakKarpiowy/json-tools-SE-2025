package pl.put.poznan.transformer.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.exceptions.JsonParsingException;
import pl.put.poznan.transformer.logic.jsonParser.JsonParserFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonParsingExceptionTest {
    // Invalid json tests
    @Test
    @DisplayName("Should throw JsonParseException when invalid JSON is provided")
    void shouldThrowJsonParseExceptionWhenInvalidJsonIsProvided() {
        String invalidJson = "{\"name\":\"test\",\"value\":}";
        assertThrows(JsonParsingException.class, () -> {
            JsonParserFactory.getParser(invalidJson, true);
        });
    }

    @Test
    @DisplayName("Should throw JsonParseException when malformed JSON is provided")
    void shouldThrowJsonParseExceptionWhenMalformedJsonIsProvided() {
        String malformedJson = "{\"name\":\"test\",\"value\":123"; // Missing closing brace
        assertThrows(JsonParsingException.class, () -> {
            JsonParserFactory.getParser(malformedJson, false);
        });
    }

    @Test
    @DisplayName("Should throw JsonParseException when empty string is provided")
    void shouldThrowJsonParseExceptionWhenEmptyStringIsProvided() {
        String emptyJson = "";
        assertThrows(JsonParsingException.class, () -> {
            JsonParserFactory.getParser(emptyJson, true);
        });
    }

    @Test
    @DisplayName("Should throw JsonParseException when null JSON is provided")
    void shouldThrowJsonParseExceptionWhenNullJsonIsProvided() {
        assertThrows(JsonParsingException.class, () -> {
            JsonParserFactory.getParser(null, true);
        });
    }
}
