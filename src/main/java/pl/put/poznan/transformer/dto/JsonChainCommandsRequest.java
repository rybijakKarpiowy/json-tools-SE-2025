package pl.put.poznan.transformer.dto;

public class JsonChainCommandsRequest {
    private String json;
    private String[] commands; // This is a list of JSONs with "command" and corresponding arguments

    public String getJson() { return json; }

    public void setJson(String json) { this.json = json; }

    public String[] getCommands() { return commands; }

    public void setCommands(String[] commands) { this.commands = commands; }
}
