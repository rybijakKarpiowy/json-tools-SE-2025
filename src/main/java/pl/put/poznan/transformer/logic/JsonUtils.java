package pl.put.poznan.transformer.logic;
import com.google.gson.*;
import com.google.gson.JsonParser;

public class JsonUtils {
    private JsonElement parse(String json) {
        return JsonParser.parseString(json);
    }

    public String Minify(String json) {
        JsonElement element = this.parse(json);
        Gson gson = new Gson();
        return gson.toJson(element);
    }

    public String Prettify(String json) {
        JsonElement element = this.parse(json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(element);
    }
}
