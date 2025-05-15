package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.dto.JsonTransformRequest;
import pl.put.poznan.transformer.dto.TextCompareRequest;
import pl.put.poznan.transformer.logic.JsonUtils;
import pl.put.poznan.transformer.logic.TextUtils;

import java.util.Arrays;


@RestController
@RequestMapping("/api/")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @PostMapping("/json/minify")
    public String minify(@RequestBody String text) {
        logger.debug("Minify json: " + text);
        JsonUtils jsonUtils = new JsonUtils();
        String out = jsonUtils.Minify(text);
        logger.debug("Minified json: " + out);
        return out;
    }

    @PostMapping("/json/prettify")
    public String prettify(@RequestBody String text) {
        logger.debug("Prettify json: " + text);
        JsonUtils jsonUtils = new JsonUtils();
        String out = jsonUtils.Prettify(text);
        logger.debug("Prettified json: " + out);
        return out;
    }

    @PostMapping("/json/query-keys")
    public String query(@RequestBody JsonTransformRequest request) {
        logger.debug("Query json keys: " + Arrays.toString(request.getKeys()) + "; json: " + request.getJson());
        JsonUtils jsonUtils = new JsonUtils();
        String out = jsonUtils.Query(request.getJson(), request.getKeys());
        return out;
    }

    @PostMapping("/json/prune-keys")
    public String prune(@RequestBody JsonTransformRequest request) {
        logger.debug("Prune json keys: " + Arrays.toString(request.getKeys()) + "; json: " + request.getJson());
        JsonUtils jsonUtils = new JsonUtils();
        String out = jsonUtils.Prune(request.getJson(), request.getKeys());
        return out;
    }

    @PostMapping("/text/compare")
    public String compare(@RequestBody TextCompareRequest request) {
        logger.debug("Compare text: " + request.getText1() + "; text2: " + request.getText2());
        TextUtils textUtils = new TextUtils();
        String out = textUtils.Diff(request.getText1(), request.getText2());
        return out;
    }
}
