package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.dto.JsonTransformRequest;
import pl.put.poznan.transformer.dto.JsonValidateStructureRequest;
import pl.put.poznan.transformer.dto.TextCompareRequest;
import pl.put.poznan.transformer.logic.TextUtils;
import pl.put.poznan.transformer.logic.jsonParser.*;

import java.util.Arrays;


@RestController
@RequestMapping("/api/")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @PostMapping("/json/minify")
    public String minify(@RequestBody String text) {
        logger.info("Minify json: " + text);

        JsonParser parser = JsonParserFactory.getParser(text, false);

        String out = parser.getString();
        logger.info("Minified json: " + out);
        return out;
    }

    @PostMapping("/json/prettify")
    public String prettify(@RequestBody String text) {
        logger.info("Prettify json: " + text);

        JsonParser parser = JsonParserFactory.getParser(text, true);

        String out = parser.getString();
        logger.info("Prettified json: " + out);
        return out;
    }

    @PostMapping("/json/query-keys")
    public String query(@RequestBody JsonTransformRequest request) {
        logger.info("Query json keys: " + Arrays.toString(request.getKeys())
                + "; json: " + request.getJson()
                + "; pretty: " + request.getPretty());

        JsonParser parser = JsonParserFactory.getParser(
                request.getJson(),
                request.getPretty(),
                DecoratorType.QUERY,
                request.getKeys()
        );

        String out = parser.getString();
        logger.info("Queried json: " + out);
        return out;
    }

    @PostMapping("/json/prune-keys")
    public String prune(@RequestBody JsonTransformRequest request) {
        logger.info("Prune json keys: " + Arrays.toString(request.getKeys())
                + "; json: " + request.getJson()
                + "; pretty: " + request.getPretty());

        JsonParser parser = JsonParserFactory.getParser(
                request.getJson(),
                request.getPretty(),
                DecoratorType.PRUNE,
                request.getKeys()
        );

        String out = parser.getString();
        logger.info("Pruned json: " + out);
        return out;
    }

    @PostMapping("/json/validate-structure")
    public String validateStructure(@RequestBody JsonValidateStructureRequest request) {
        logger.info("Validate json structure: " + request.getJson()
                + "; structure: " + request.getStructure()
                + "; pretty: " + request.getPretty());

        JsonParser parser = JsonParserFactory.getParser(
                request.getJson(),
                request.getPretty(),
                DecoratorType.VALIDATE,
                new String[]{request.getStructure()}
        );

        String out = parser.getString();
        logger.info("Is valid structure: " + out);
        return out;
    }

    @PostMapping("/text/compare")
    public String compare(@RequestBody TextCompareRequest request) {
        logger.info("Compare text: " + request.getText1() + "; text2: " + request.getText2());

        String out = TextUtils.diff(request.getText1(), request.getText2());
        logger.info("Compared text: " + out);
        return out;
    }
}
