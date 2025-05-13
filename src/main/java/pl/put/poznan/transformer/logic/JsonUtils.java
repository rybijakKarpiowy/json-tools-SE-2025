package pl.put.poznan.transformer.logic;

import com.google.gson.*;
import pl.put.poznan.transformer.exceptions.JsonParsingException;

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
            String[] parts = key.split("\\.");
            JsonElement val = this.getNestedValue(obj, parts, 0);
            if (val != null) {
                this.addNested(filtered, parts, 0, val);
            }
        }

        return this.getString(filtered);
    }

    public String Prune(String json, String[] keys) {
        JsonElement element = this.parse(json);
        JsonObject pruned = element.getAsJsonObject();

        for (String key : keys) {
            String[] parts = key.split("\\.");
            this.removeNested(pruned, parts, 0);
        }

        return this.getString(pruned);
    }

    /**
     * Recursively retrieves a nested value from a JsonObject using key parts.
     */
    private JsonElement getNestedValue(JsonObject obj, String[] parts, int idx) {
        if (idx >= parts.length) return obj;
        if (!obj.has(parts[idx])) return null;
        JsonElement el = obj.get(parts[idx]);
        if (idx == parts.length - 1) {
            return el;
        }
        if (el.isJsonObject()) {
            return getNestedValue(el.getAsJsonObject(), parts, idx + 1);
        }
        return null;
    }

    /**
     * Recursively adds a value to a JsonObject at the nested path specified by parts.
     */
    private void addNested(JsonObject obj, String[] parts, int idx, JsonElement value) {
        String key = parts[idx];
        if (idx == parts.length - 1) {
            obj.add(key, value);
            return;
        }
        JsonObject child;
        if (obj.has(key) && obj.get(key).isJsonObject()) {
            child = obj.getAsJsonObject(key);
        } else {
            child = new JsonObject();
            obj.add(key, child);
        }
        addNested(child, parts, idx + 1, value);
    }

    /**
     * Recursively removes a nested key from a JsonObject using key parts.
     */
    private void removeNested(JsonObject obj, String[] parts, int idx) {
        if (idx >= parts.length - 1) {
            obj.remove(parts[idx]);
            return;
        }
        if (obj.has(parts[idx]) && obj.get(parts[idx]).isJsonObject()) {
            JsonObject child = obj.getAsJsonObject(parts[idx]);
            removeNested(child, parts, idx + 1);
        }
    }
}
