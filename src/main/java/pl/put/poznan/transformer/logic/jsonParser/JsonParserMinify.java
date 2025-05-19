package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.Gson;

public class JsonParserMinify extends JsonParser {
    public JsonParserMinify(String jsonRaw) {
        super(jsonRaw);
    }

    @Override
    public String getString() {
        Gson gson = new Gson();
        return gson.toJson(this.json);
    }
}
