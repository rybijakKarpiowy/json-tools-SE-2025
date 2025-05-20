package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A JSON parser that formats JSON strings with proper indentation and line breaks.
 * This class extends the base JsonParser to provide pretty-printed JSON output.
 */
public class JsonParserPrettify extends JsonParser {
    /**
     * Constructs a new JsonParserPrettify instance.
     *
     * @param jsonRaw the raw JSON string to be parsed and prettified
     */
    public JsonParserPrettify(String jsonRaw) {
        super(jsonRaw);
    }

    /**
     * Converts the parsed JSON object back to a string with pretty formatting.
     *
     * @return a properly formatted JSON string with indentation and line breaks
     */
    @Override
    public String getString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this.json);
    }
}
