package pl.put.poznan.transformer.dto;

/**
 * Abstract base class for JSON-related requests.
 * Contains common fields: json and pretty.
 */
public abstract class AbstractJsonRequest {
    protected String json;
    protected Boolean pretty;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Boolean getPretty() {
        return pretty;
    }

    public void setPretty(Boolean pretty) {
        this.pretty = pretty;
    }
}