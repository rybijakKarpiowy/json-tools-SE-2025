package pl.put.poznan.transformer.dto;

public class TextCompareRequest {
    private String text1;
    private String text2;

    // Getters and setters
    public String getText1() {
        return text1;
    }

    public void setText1(String text) {
        this.text1 = text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text) {
        this.text2 = text;
    }
}
