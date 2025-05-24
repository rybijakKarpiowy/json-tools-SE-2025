package pl.put.poznan.transformer.dto;

public class JsonTransformRequest extends AbstractJsonRequest {
    private String[] keys;

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }
}