package pl.put.poznan.transformer.logic.jsonParser;

import pl.put.poznan.transformer.logic.jsonParser.JsonParserSimple;
import pl.put.poznan.transformer.dto.JsonStructureValidationResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

/**
 * A decorator for JsonParser that validates whether a JSON object matches a given structure.
 * The structure is provided as a JSON string, and only the presence and nesting of keys is checked,
 * not the actual values or types.
 */
public class JsonParserStructureValidatorDecorator extends JsonParserDecorator {
    private final JsonObject structure;
    private boolean isValid;
    private String difference;

    /**
     * Creates a new JsonParserStructureValidatorDecorator instance.
     *
     * @param parser    The JsonParser instance to be decorated
     * @param structureJson JSON string representing the required structure
     */
    public JsonParserStructureValidatorDecorator(JsonParser parser, String[] keys) {
        super(parser, keys);
        String structureJson = keys[0];

        JsonParserSimple structureParser = new JsonParserSimple(structureJson); // Validate the structure JSON format
        JsonElement structure = structureParser.GetJsonElement();
        this.structure = structure.getAsJsonObject();

        logger.debug("Creating JsonParserStructureValidatorDecorator with parser: " + parser.getClass().getSimpleName() + " and structure: " + structureJson);

        this.validateStructure();
    }

    /**
     * Validates whether the internal JSON matches the required structure.
     *
     * @return true if the structure matches, false otherwise
     */
    private boolean validateStructure() {
        logger.debug("Validating JSON structure: " + this.json.getAsJsonObject());
        isValid = validateRecursive(this.structure, this.json.getAsJsonObject());

        JsonStructureValidationResponse response = new JsonStructureValidationResponse(isValid, difference);

        Gson gson = new Gson();
        this.json = gson.toJsonTree(response);
        return isValid;
    }

    /**
     * Recursively checks if all keys and nested objects in the structure are present in the target.
     *
     * @param structureObj The structure to check against
     * @param targetObj    The JSON object to validate
     * @return true if targetObj matches structureObj, false otherwise
     */
    private boolean validateRecursive(JsonObject structureObj, JsonObject targetObj) {
        for (String key : structureObj.keySet()) {
            if (!targetObj.has(key)) {
                this.difference = "Missing key: " + key;
                return false;
            }
            JsonElement structElem = structureObj.get(key);
            JsonElement targetElem = targetObj.get(key);

            // Compare types
            if (structElem.isJsonObject()) {
                if (!targetElem.isJsonObject()) {
                    this.difference = "Key '" + key + "' should be an object.";
                    return false;
                }
                if (!validateRecursive(structElem.getAsJsonObject(), targetElem.getAsJsonObject())) {
                    this.difference = "Difference in object at key: " + key + (this.difference != null ? " -> " + this.difference : "");
                    return false;
                }
            } else if (structElem.isJsonArray()) {
                if (!targetElem.isJsonArray()) {
                    this.difference = "Key '" + key + "' should be an array.";
                    return false;
                }
                // Optionally, compare array element types if needed
            } else if (structElem.isJsonPrimitive()) {
                if (!targetElem.isJsonPrimitive()) {
                    this.difference = "Key '" + key + "' should be a primitive value.";
                    return false;
                }
                // Compare primitive types (string, number, boolean)
                if (structElem.getAsJsonPrimitive().isString() != targetElem.getAsJsonPrimitive().isString()
                    || structElem.getAsJsonPrimitive().isNumber() != targetElem.getAsJsonPrimitive().isNumber()
                    || structElem.getAsJsonPrimitive().isBoolean() != targetElem.getAsJsonPrimitive().isBoolean()) {
                    this.difference = "Key '" + key + "' has different primitive type.";
                    return false;
                }
            } else if (structElem.isJsonNull()) {
                if (!targetElem.isJsonNull()) {
                    this.difference = "Key '" + key + "' should be null.";
                    return false;
                }
            }
        }
        return true;
    }
}