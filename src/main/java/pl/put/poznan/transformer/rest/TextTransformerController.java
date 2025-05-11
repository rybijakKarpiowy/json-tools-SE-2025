package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.dto.JsonTransformRequest;
import pl.put.poznan.transformer.dto.TextCompareRequest;
import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;


@RestController
@RequestMapping("/api/")
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    @GetMapping("/text/to-upper/{text}")
    public String get(@PathVariable String text,
                              @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        // perform the transformation, you should run your logic here, below is just a silly example
        TextTransformer transformer = new TextTransformer(transforms);
        return transformer.transform(text);
    }

    @PostMapping("/text/to-upper/{text}")
    public String post(@PathVariable String text, @RequestBody String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        // perform the transformation, you should run your logic here, below is just a silly example
        TextTransformer transformer = new TextTransformer(transforms);
        return transformer.transform(text);
    }

    @PostMapping("/json/minify")
    public String minify(@RequestBody String text) {
        logger.debug("Minify json: " + text);
        // TODO: minify json
        return "";
    }

    @PostMapping("/json/query-keys")
    public String minify(@RequestBody JsonTransformRequest request) {
        logger.debug("Query json keys: " + Arrays.toString(request.getKeys()) + "; json: " + request.getJson());
        // TODO: query specific keys
        return "";
    }

    @PostMapping("/json/prune-keys")
    public String prune(@RequestBody JsonTransformRequest request) {
        logger.debug("Prune json keys: " + Arrays.toString(request.getKeys()) + "; json: " + request.getJson());
        return "";
    }

    @PostMapping("/text/compare")
    public String compare(@RequestBody TextCompareRequest request) {
        logger.debug("Compare text: " + request.getText1() + "; text2: " + request.getText2());
        return "";
    }
}

