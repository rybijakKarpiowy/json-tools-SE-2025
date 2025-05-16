package pl.put.poznan.transformer.logic.jsonParser;

public abstract class JsonParserDecorator extends JsonParser {
    protected String[] keys;
    protected JsonParser parser;

    public JsonParserDecorator(JsonParser parser, String[] keys) {
        super(parser.getString());
        this.keys = keys;
        this.parser = parser;
    }

    @Override
    public String getString() {
        this.parser.json = this.json;
        return this.parser.getString();
    }
}
