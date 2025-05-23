package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.exceptions.JsonParsingException;


/**
 * Abstract base class for JSON parsing and transformation operations.
 * Provides basic JSON parsing functionality using Google's Gson library.
 */
public abstract class JsonParser {
    /**
     * Internal JSON field in which the parsed / transformed
     * JSON structure is stored.
     * Protected field accessible to subclasses for JSON manipulation.
     */
    protected JsonElement json;

    protected static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    /**
     * Parses a string into a JsonElement.
     *
     * @param text The JSON string to parse
     * @return The parsed JsonElement
     * @throws JsonParsingException if the input string is not valid JSON
     */
    private JsonElement parse(String text) {
        try {
            return com.google.gson.JsonParser.parseString(text);
        } catch (Exception e) {
            throw new JsonParsingException("Invalid JSON: " + text, e);
        }
    }

    /**
     * Constructs a new JsonParser with the given JSON string.
     *
     * @param jsonRaw The raw JSON string to be parsed
     * @throws JsonParsingException if the input string is not valid JSON
     */
    public JsonParser(String jsonRaw) {
        logger.debug("Creating JsonParser with raw JSON: " + jsonRaw);
        this.json = this.parse(jsonRaw);
    }

    /**
     * Returns a string representation of the parsed JSON.
     *
     * @return The JSON string in the desired format
     */
    public abstract String getString();
}
