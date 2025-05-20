package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.JsonObject;


/**
 * A decorator for JsonParser that removes specified keys from the JSON structure.
 * This decorator implements the pruning functionality, allowing removal of both
 * top-level and nested keys from the JSON object using dot notation.
 */
public class JsonParserPruneDecorator extends JsonParserDecorator {
    /**
     * Creates a new JsonParserPruneDecorator instance.
     * It prunes the internal JSON object on construction.
     *
     * @param parser The JsonParser instance to be decorated
     * @param keys   Array of keys to be removed from the JSON structure. For nested keys,
     *               use dot notation (e.g., "parent.child.grandchild")
     */
    public JsonParserPruneDecorator(JsonParser parser, String[] keys) {
        super(parser, keys);
        this.prune();
    }

    /**
     * Processes the JSON object and removes all specified keys.
     * This method splits each key by dots to handle nested structures
     * and delegates the actual removal to removeNested method.
     */
    private void prune() {
        JsonObject pruned = this.json.getAsJsonObject();

        for (String key : this.keys) {
            String[] parts = key.split("\\.");
            this.removeNested(pruned, parts, 0);
        }

        this.json = pruned;
    }

    /**
     * Recursively removes a nested key from a JsonObject using key parts.
     *
     * @param obj The JsonObject to remove keys from
     * @param parts Array of key parts split by dots representing the path to the target
     * @param idx Current index in the parts array indicating processing depth
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
