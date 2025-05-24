package pl.put.poznan.transformer.dto;

public class JsonValidateStructureRequest extends AbstractJsonRequest {
    private String structure;

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
}