package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.JsonElement;


public class JsonParserSimple extends JsonParser {
    public JsonParserSimple(String rawJson) {
        super(rawJson);
    }

    public JsonElement GetJsonElement() {
        return this.json;
    }

    @Override
    public String getString() {
        return this.json.toString();
    }
}
