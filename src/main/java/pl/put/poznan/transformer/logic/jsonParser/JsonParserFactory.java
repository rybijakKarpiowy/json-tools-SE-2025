package pl.put.poznan.transformer.logic.jsonParser;


/**
 * Factory class for creating JSON parser instances with different configurations.
 * Supports creation of basic parsers and decorated parsers with various functionalities.
 */
public class JsonParserFactory {

    /**
     * Creates a basic JSON parser instance.
     *
     * @param jsonRaw The raw JSON string to be parsed
     * @param pretty  Boolean flag indicating whether to prettify (true) or minify (false) the output
     * @return A JsonParser instance configured for basic parsing
     */
    public static JsonParser getParser(String jsonRaw, Boolean pretty) {
        if (pretty) {
            return new JsonParserPrettify(jsonRaw);
        } else {
            return new JsonParserMinify(jsonRaw);
        }
    }

    /**
     * Creates a decorated JSON parser instance with a single decorator.
     *
     * @param jsonRaw       The raw JSON string to be parsed
     * @param pretty        Boolean flag indicating whether to prettify (true) or minify (false) the output
     * @param decoratorType The type of decorator to apply (PRUNE or QUERY)
     * @param keys          Array of keys to be used by the decorator
     * @return A decorated JsonParser instance
     */
    public static JsonParser getParser(String jsonRaw, Boolean pretty, DecoratorType decoratorType, String[] keys) {
        JsonParser parser = getParser(jsonRaw, pretty);
        switch (decoratorType) {
            case PRUNE:
                return new JsonParserPruneDecorator(parser, keys);
            case QUERY:
                return new JsonParserQueryDecorator(parser, keys);
            default:
                return parser;
        }
    }

    /**
     * Creates a JSON parser instance with multiple decorators chained together.
     *
     * @param jsonRaw        The raw JSON string to be parsed
     * @param pretty         Boolean flag indicating whether to prettify (true) or minify (false) the output
     * @param decoratorTypes Array of decorator types to be applied in sequence
     * @param keysArray      Array of key arrays, each corresponding to a decorator
     * @return A JsonParser instance with chained decorators
     * @throws IllegalArgumentException if the number of decorators doesn't match the number of key arrays
     */
    public static JsonParser getParser(String jsonRaw, Boolean pretty, DecoratorType[] decoratorTypes, String[][] keysArray) {
        if (keysArray.length != decoratorTypes.length) {
            throw new IllegalArgumentException("Number of keys and decorators must be equal!");
        }

        JsonParser parser = getParser(jsonRaw, pretty);
        for (int i = 0; i < decoratorTypes.length; i++) {
            parser = getParser(parser.getString(), pretty, decoratorTypes[i], keysArray[i]);
        }
        return parser;
    }
}