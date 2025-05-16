package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.JsonElement;
import pl.put.poznan.transformer.exceptions.JsonParsingException;

public abstract class JsonParser {
    protected JsonElement json;

    private JsonElement parse(String text) {
        try {
            return com.google.gson.JsonParser.parseString(text);
        } catch (Exception e) {
            throw new JsonParsingException("Invalid JSON: " + text, e);
        }
    }

    public JsonParser(String jsonRaw) {
        this.json = this.parse(jsonRaw);
    }

    public abstract String getString();
}
