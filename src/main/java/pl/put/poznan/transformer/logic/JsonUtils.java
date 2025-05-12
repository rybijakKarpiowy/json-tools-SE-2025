package pl.put.poznan.transformer.logic;
import com.google.gson.*;
import pl.put.poznan.transformer.exceptions.JsonParsingException;

import java.util.Arrays;
import java.util.List;

public class JsonUtils {
    private JsonElement parse(String text) {
        try {
            return JsonParser.parseString(text);
        } catch (Exception e) {
            throw new JsonParsingException("Invalid JSON: " + text, e);
        }
    }

    private String getString(JsonElement json) {
        Gson gson = new Gson();
        return gson.toJson(json);
    }

    private String getString(JsonElement json, Gson gson) {
        return gson.toJson(json);
    }

    public String Minify(String json) {
        JsonElement element = this.parse(json);
        return this.getString(element);
    }

    public String Prettify(String json) {
        JsonElement element = this.parse(json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return this.getString(element, gson);
    }

    public String Query(String json, String[] keys) {
        JsonElement element = this.parse(json);
        JsonObject obj = element.getAsJsonObject();
        JsonObject filtered = new JsonObject();

        for (String key : keys) {
            if (obj.has(key)) {
                JsonElement val = obj.get(key);
                filtered.add(key, val);
            }
        }

        return this.getString(filtered);
    }

    public String Prune(String json, String[] keys) {
        JsonElement element = this.parse(json);
        JsonObject obj = element.getAsJsonObject();
        JsonObject filtered = new JsonObject();
        List<String> keysList = Arrays.asList(keys);

        for (String key : obj.keySet()) {
            System.out.println(key);
            if (!keysList.contains(key)) {
                JsonElement val = obj.get(key);
                filtered.add(key, val);
            }
        }

        return this.getString(filtered);
    }
}
