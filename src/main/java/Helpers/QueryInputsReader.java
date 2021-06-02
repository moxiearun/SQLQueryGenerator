package Helpers;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * QueryReader helps to read the query details from json file.
 */

public class QueryInputsReader {

    private final String queryFilePath;

    public QueryInputsReader(String queryFilePath) {
        this.queryFilePath = queryFilePath;
    }

    /**
     * Reads the query inputs from json file and returns as JSONObject
     *
     * @return query inputs from json file
     */
    public JSONObject readQueryInputs() {
        JSONObject queryInputs = null;
        try {
            JSONParser jsonParser = new JSONParser();
            queryInputs = (JSONObject) jsonParser.parse(new FileReader(queryFilePath));

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return queryInputs;
    }

}
