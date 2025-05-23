package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * A decorator class for JSON parsing that filters JSON objects to include only specified keys.
 * It supports nested key paths using dot notation (e.g., "parent.child").
 */
public class JsonParserQueryDecorator extends JsonParserDecorator {
    /**
     * Constructs a new JsonParserQueryDecorator.
     * It filters the internal JSON object on construction.
     *
     * @param parser The base JsonParser to decorate
     * @param keys   Array of keys to keep in the JSON. Can include nested paths using dot notation
     */
    public JsonParserQueryDecorator(JsonParser parser, String[] keys) {
        super(parser, keys);
        logger.debug("Creating JsonParserQueryDecorator with parser: " + parser.getClass().getSimpleName() + " and keys: " + String.join(", ", keys));
        this.query();
    }

    /**
     * Modifies the JsonElement by filtering out keys not specified in the keys array.
     */
    private void query() {
        JsonObject obj = this.json.getAsJsonObject();
        JsonObject filtered = new JsonObject();

        for (String key : this.keys) {
            String[] parts = key.split("\\.");
            JsonElement val = this.getNestedValue(obj, parts, 0);
            if (val != null) {
                this.addNested(filtered, parts, 0, val);
            }
        }

        this.json = filtered;
    }

    /**
     * Recursively retrieves a nested value from a JsonObject using key parts.
     *
     * @param obj   The JsonObject to search in
     * @param parts Array of key parts representing the path to the desired value
     * @param idx   Current index in the parts array
     * @return The JsonElement at the specified path, or null if not found
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
     *
     * @param obj   The JsonObject to add to
     * @param parts Array of key parts representing the path where to add the value
     * @param idx   Current index in the parts array
     * @param value The JsonElement to add at the specified path
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
}
