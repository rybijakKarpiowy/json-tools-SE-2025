package pl.put.poznan.transformer.logic.jsonParser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

class JsonParserFactoryTest {

    private static final String SAMPLE_JSON = "{\"name\":\"test\",\"value\":123}";

    @Test
    @DisplayName("Should create JsonParserPrettify when pretty is true")
    void shouldCreateJsonParserPrettifyWhenPrettyIsTrue() {
        JsonParser parser = JsonParserFactory.getParser(SAMPLE_JSON, true);
        assertInstanceOf(JsonParserPrettify.class, parser);
    }

    @Test
    @DisplayName("Should create JsonParserMinify when pretty is false")
    void shouldCreateJsonParserMinifyWhenPrettyIsFalse() {
        JsonParser parser = JsonParserFactory.getParser(SAMPLE_JSON, false);
        assertInstanceOf(JsonParserMinify.class, parser);
    }

    @Test
    @DisplayName("Should create JsonParserPruneDecorator when decorator type is PRUNE")
    void shouldCreateJsonParserPruneDecoratorWhenDecoratorTypeIsPrune() {
        String[] keys = {"key1", "key2"};
        JsonParser parser = JsonParserFactory.getParser(SAMPLE_JSON, true, DecoratorType.PRUNE, keys);
        assertInstanceOf(JsonParserPruneDecorator.class, parser);
    }

    @Test
    @DisplayName("Should create JsonParserQueryDecorator when decorator type is QUERY")
    void shouldCreateJsonParserQueryDecoratorWhenDecoratorTypeIsQuery() {
        String[] keys = {"key1", "key2"};
        JsonParser parser = JsonParserFactory.getParser(SAMPLE_JSON, false, DecoratorType.QUERY, keys);
        assertInstanceOf(JsonParserQueryDecorator.class, parser);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decorators and keys arrays have different lengths")
    void shouldThrowIllegalArgumentExceptionWhenDecoratorsAndKeysArraysHaveDifferentLengths() {
        DecoratorType[] decoratorTypes = {DecoratorType.PRUNE, DecoratorType.QUERY};
        String[][] keysArray = {{"key1"}};
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> JsonParserFactory.getParser(SAMPLE_JSON, true, decoratorTypes, keysArray)
        );

        assertEquals("Number of keys and decorators must be equal!", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle empty decorator arrays")
    void shouldHandleEmptyDecoratorArrays() {
        DecoratorType[] decoratorTypes = {};
        String[][] keysArray = {};
        JsonParser parser = JsonParserFactory.getParser(SAMPLE_JSON, true, decoratorTypes, keysArray);
        assertNotNull(parser);
        assertInstanceOf(JsonParserPrettify.class, parser);
    }

    @Test
    @DisplayName("Should handle single decorator in array")
    void shouldHandleSingleDecoratorInArray() {
        DecoratorType[] decoratorTypes = {DecoratorType.PRUNE};
        String[][] keysArray = {{"key1", "key2"}};
        JsonParser parser = JsonParserFactory.getParser(SAMPLE_JSON, false, decoratorTypes, keysArray);
        assertNotNull(parser);
        assertInstanceOf(JsonParserPruneDecorator.class, parser);
    }

}