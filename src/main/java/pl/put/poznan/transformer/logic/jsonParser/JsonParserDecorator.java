package pl.put.poznan.transformer.logic.jsonParser;


/**
 * Abstract decorator class for JSON parsing operations.
 * Implements the Decorator pattern to allow for dynamic addition
 * of JSON transformation behaviors.
 */
public abstract class JsonParserDecorator extends JsonParser {
    /**
     * Array of keys used in JSON transformation or filtering processes.
     * These keys may represent paths in the JSON structure, typically used
     * by decorator classes to prune or query specific fields from a JSON document.
     */
    protected String[] keys;

    /**
     * The wrapped JsonParser instance that this decorator enhances.
     */
    protected JsonParser parser;

    /**
     * Constructs a new JsonParserDecorator with the specified parser and keys.
     *
     * @param parser The base JsonParser to be decorated
     * @param keys   Array of keys to be used in JSON transformation
     */
    public JsonParserDecorator(JsonParser parser, String[] keys) {
        super(parser.getString());
        this.keys = keys;
        this.parser = parser;
    }

    /**
     * Returns the transformed JSON string.
     * Updates the wrapped parser's JSON before getting its string representation.
     *
     * @return The transformed JSON string
     */
    @Override
    public String getString() {
        this.parser.json = this.json;
        return this.parser.getString();
    }
}
