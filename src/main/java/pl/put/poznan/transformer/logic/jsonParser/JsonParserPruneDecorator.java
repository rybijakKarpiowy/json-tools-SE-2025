package pl.put.poznan.transformer.logic.jsonParser;

import com.google.gson.JsonObject;

public class JsonParserPruneDecorator extends JsonParserDecorator {
    public JsonParserPruneDecorator(JsonParser parser, String[] keys) {
        super(parser, keys);
        this.prune();
    }

    public void prune() {
        JsonObject pruned = this.json.getAsJsonObject();

        for (String key : this.keys) {
            String[] parts = key.split("\\.");
            this.removeNested(pruned, parts, 0);
        }

        this.json = pruned;
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
