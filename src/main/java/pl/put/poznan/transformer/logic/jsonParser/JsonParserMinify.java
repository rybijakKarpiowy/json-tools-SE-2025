package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.Gson;


/**
 * A JSON parser implementation that minifies JSON content by removing unnecessary whitespace
 * and formatting. This class extends the base JsonParser class and uses Gson for JSON processing.
 */
public class JsonParserMinify extends JsonParser {
    /**
     * Constructs a new JsonParserMinify with the given raw JSON string.
     *
     * @param jsonRaw The raw JSON string to be minified
     */
    public JsonParserMinify(String jsonRaw) {
        super(jsonRaw);
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
