package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParserPrettify extends JsonParser {
    public JsonParserPrettify(String jsonRaw) {
        super(jsonRaw);
    }

    @Override
    public String getString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this.json);
    }
}
