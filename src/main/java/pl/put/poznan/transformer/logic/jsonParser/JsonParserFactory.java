package pl.put.poznan.transformer.logic.jsonParser;

public class JsonParserFactory {

    // Default parser
    public static JsonParser getParser(String jsonRaw, Boolean pretty) {
        if (pretty) {
            return new JsonParserPrettify(jsonRaw);
        } else {
            return new JsonParserMinify(jsonRaw);
        }
    }

    // Decorated parser
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

    // Chain of decorators
    public static JsonParser getParser(String jsonRaw, Boolean pretty, DecoratorType[] decoratorTypes, String[][] keysArray) {
        JsonParser parser = getParser(jsonRaw, pretty);
        for (int i = 0; i < decoratorTypes.length; i++) {
            parser = getParser(parser.getString(), pretty, decoratorTypes[i], keysArray[i]);
        }
        return parser;
    }
}