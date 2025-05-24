package pl.put.poznan.transformer.logic.jsonParser;

import pl.put.poznan.transformer.logic.jsonParser.JsonParserSimple;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A decorator for JsonParser that validates whether a JSON object matches a given structure.
 * The structure is provided as a JSON string, and only the presence and nesting of keys is checked,
 * not the actual values or types.
 */
public class JsonParserStructureValidatorDecorator extends JsonParserDecorator {
    private final JsonObject structure;
    private boolean isValid;

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
        isValid = this.validateStructure();
    }

    /** 
     * Returns a string representation of the JSON structure validation result.
     * If the structure matches, it returns a success message; otherwise, it returns an error message.
     * This method is used to provide feedback on the validation process.
     */
    @Override
    public String getString() {
        logger.debug("Validating JSON structure: " + this.json.getAsJsonObject());
        if (isValid) {
            return "JSON structure is valid.";
        } else {
            return "JSON structure is invalid.";
        }
    }

    /**
     * Validates whether the internal JSON matches the required structure.
     *
     * @return true if the structure matches, false otherwise
     */
    public boolean validateStructure() {
        return validateRecursive(this.structure, this.json.getAsJsonObject());
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
                return false;
            }
            JsonElement structElem = structureObj.get(key);
            JsonElement targetElem = targetObj.get(key);
            if (structElem.isJsonObject()) {
                if (!targetElem.isJsonObject()) {
                    return false;
                }
                if (!validateRecursive(structElem.getAsJsonObject(), targetElem.getAsJsonObject())) {
                    return false;
                }
            }
        }
        return true;
    }
}