package pl.put.poznan.transformer.exceptions;

public class JsonParsingException extends RuntimeException {
    public JsonParsingException(String message, Throwable cause) {
        super(message, cause);
    }
    public JsonParsingException(String message) {
        super(message);
    }
}