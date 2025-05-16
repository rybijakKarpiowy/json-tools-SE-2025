package pl.put.poznan.transformer.dto;

public class JsonTransformRequest {
    private String json;
    private String[] keys;
    private Boolean pretty;

    // Getters and setters
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public Boolean getPretty() { return pretty; }

    public void setPretty(Boolean pretty) { this.pretty = pretty; }
}