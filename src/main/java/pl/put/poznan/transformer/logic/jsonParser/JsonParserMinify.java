package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A JSON parser implementation that minifies JSON content by removing unnecessary whitespace
 * and formatting. This class extends the base JsonParser class and uses Gson for JSON processing.
 */
public class JsonParserMinify extends JsonParser {
    private static final Logger logger = LoggerFactory.getLogger(JsonParserMinify.class);

    /**
     * Constructs a new JsonParserMinify with the given raw JSON string.
     *
     * @param jsonRaw The raw JSON string to be minified
     */
    public JsonParserMinify(String jsonRaw) {
        super(jsonRaw);
        logger.debug("Creating minified JSON parser with input: " + jsonRaw);
    }

    /**
     * Returns the minified JSON string representation of the parsed JSON content.
     *
     * @return A string containing the minified JSON
     */
    @Override
    public String getString() {
        Gson gson = new Gson();
        return gson.toJson(this.json);
    }
}
