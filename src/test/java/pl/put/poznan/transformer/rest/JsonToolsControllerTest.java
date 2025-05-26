package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.put.poznan.transformer.dto.JsonTransformRequest;
import pl.put.poznan.transformer.dto.TextCompareRequest;
import pl.put.poznan.transformer.logic.TextUtils;
import pl.put.poznan.transformer.logic.jsonParser.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonToolsControllerTest {

    @Mock
    private JsonParser mockParser;

    @InjectMocks
    private JsonToolsController controller;
    private static final String SAMPLE_JSON = "{\"name\":\"test\",\"value\":123}";
    private static final String EXPECTED_OUTPUT = "mocked_output";

    @BeforeEach
    void setUp() {
        lenient().when(mockParser.getString()).thenReturn(EXPECTED_OUTPUT);
    }

    @Test
    @DisplayName("Should minify JSON and return result")
    void shouldMinifyJsonAndReturnResult() {
        String inputJson = SAMPLE_JSON;

        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(inputJson, false))
                    .thenReturn(mockParser);

            String result = controller.minify(inputJson);
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(inputJson, false));
            verify(mockParser).getString();
        }
    }

    @Test
    @DisplayName("Should prettify JSON and return result")
    void shouldPrettifyJsonAndReturnResult() {
        String inputJson = SAMPLE_JSON;
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(inputJson, true))
                    .thenReturn(mockParser);
            String result = controller.prettify(inputJson);
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(inputJson, true));
            verify(mockParser).getString();
        }
    }

    @Test
    @DisplayName("Should query JSON keys and return result")
    void shouldQueryJsonKeysAndReturnResult() {
        JsonTransformRequest request = new JsonTransformRequest();
        request.setJson(SAMPLE_JSON);
        request.setPretty(true);
        request.setKeys(new String[]{"name", "value"});
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(
                            request.getJson(),
                            request.getPretty(),
                            DecoratorType.QUERY,
                            request.getKeys()))
                    .thenReturn(mockParser);
            String result = controller.query(request);
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(
                    request.getJson(),
                    request.getPretty(),
                    DecoratorType.QUERY,
                    request.getKeys()));
            verify(mockParser).getString();
        }
    }

    @Test
    @DisplayName("Should query JSON keys with minify option")
    void shouldQueryJsonKeysWithMinifyOption() {
        JsonTransformRequest request = new JsonTransformRequest();
        request.setJson(SAMPLE_JSON);
        request.setPretty(false); // minify
        request.setKeys(new String[]{"name"});
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(
                            request.getJson(),
                            false,
                            DecoratorType.QUERY,
                            request.getKeys()))
                    .thenReturn(mockParser);
            String result = controller.query(request);
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(
                    request.getJson(),
                    false,
                    DecoratorType.QUERY,
                    request.getKeys()));
        }
    }

    @Test
    @DisplayName("Should prune JSON keys and return result")
    void shouldPruneJsonKeysAndReturnResult() {
        JsonTransformRequest request = new JsonTransformRequest();
        request.setJson(SAMPLE_JSON);
        request.setPretty(true);
        request.setKeys(new String[]{"unwanted_key"});
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(
                            request.getJson(),
                            request.getPretty(),
                            DecoratorType.PRUNE,
                            request.getKeys()))
                    .thenReturn(mockParser);

            String result = controller.prune(request);
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(
                    request.getJson(),
                    request.getPretty(),
                    DecoratorType.PRUNE,
                    request.getKeys()));
            verify(mockParser).getString();
        }
    }

    @Test
    @DisplayName("Should prune JSON keys with minify option")
    void shouldPruneJsonKeysWithMinifyOption() {
        // Given
        JsonTransformRequest request = new JsonTransformRequest();
        request.setJson(SAMPLE_JSON);
        request.setPretty(false); // minify
        request.setKeys(new String[]{"key1", "key2", "key3"});

        // When & Then
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(
                            request.getJson(),
                            false,
                            DecoratorType.PRUNE,
                            request.getKeys()))
                    .thenReturn(mockParser);

            // When
            String result = controller.prune(request);

            // Then
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(
                    request.getJson(),
                    false,
                    DecoratorType.PRUNE,
                    request.getKeys()));
        }
    }

    @Test
    @DisplayName("Should compare texts and return diff result")
    void shouldCompareTextsAndReturnDiffResult() {
        // Given
        TextCompareRequest request = new TextCompareRequest();
        request.setText1("line1\nline2");
        request.setText2("line1\nmodified_line2");
        String expectedDiff = "Difference found at line 2:\n  -  line2\n  +  modified_line2";

        // When & Then
        try (MockedStatic<TextUtils> mockedTextUtils = mockStatic(TextUtils.class)) {
            mockedTextUtils.when(() -> TextUtils.diff(request.getText1(), request.getText2()))
                    .thenReturn(expectedDiff);

            // When
            String result = controller.compare(request);

            // Then
            assertEquals(expectedDiff, result);
            mockedTextUtils.verify(() -> TextUtils.diff(request.getText1(), request.getText2()));
        }
    }

    @Test
    @DisplayName("Should compare identical texts and return empty result")
    void shouldCompareIdenticalTextsAndReturnEmptyResult() {
        // Given
        TextCompareRequest request = new TextCompareRequest();
        request.setText1("same text");
        request.setText2("same text");

        // When & Then
        try (MockedStatic<TextUtils> mockedTextUtils = mockStatic(TextUtils.class)) {
            mockedTextUtils.when(() -> TextUtils.diff(request.getText1(), request.getText2()))
                    .thenReturn("");

            // When
            String result = controller.compare(request);

            // Then
            assertEquals("", result);
            mockedTextUtils.verify(() -> TextUtils.diff(request.getText1(), request.getText2()));
        }
    }

    @Test
    @DisplayName("Should handle empty JSON input for minify")
    void shouldHandleEmptyJsonInputForMinify() {
        // Given
        String emptyJson = "";

        // When & Then
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(emptyJson, false))
                    .thenReturn(mockParser);

            // When
            String result = controller.minify(emptyJson);

            // Then
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(emptyJson, false));
        }
    }

    @Test
    @DisplayName("Should handle empty JSON input for prettify")
    void shouldHandleEmptyJsonInputForPrettify() {
        // Given
        String emptyJson = "";

        // When & Then
        try (MockedStatic<JsonParserFactory> mockedFactory = mockStatic(JsonParserFactory.class)) {
            mockedFactory.when(() -> JsonParserFactory.getParser(emptyJson, true))
                    .thenReturn(mockParser);

            // When
            String result = controller.prettify(emptyJson);

            // Then
            assertEquals(EXPECTED_OUTPUT, result);
            mockedFactory.verify(() -> JsonParserFactory.getParser(emptyJson, true));
        }
    }

}