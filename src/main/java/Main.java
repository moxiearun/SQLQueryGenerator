import Helpers.QueryConstructor;
import Helpers.QueryInputsReader;
import Interfaces.QueryDisplayListener;
import Models.Query;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class Main {

    public static void main(String[] arg) {

        // Reads the json file and returns the query inputs as json object
        JSONObject queryInputs =
                new QueryInputsReader("/Users/755774/IdeaProjects/SQLQueryGenerator/src/main/resources/query.json")
                        .readQueryInputs();

        // Converts the json object into objects
        Gson gson = new Gson();
        Query sqlQuery = gson.fromJson(queryInputs.toString(), Query.class);

        // Constructs the query from objects
        QueryConstructor queryConstructor = new QueryConstructor(sqlQuery);
        queryConstructor.constructQuery(new QueryDisplayListener() {
            @Override
            public void displayConstructedQuery(String query) {
                System.out.println(query);
            }

            @Override
            public void displayException(String exceptionMessage) {
                System.out.println(exceptionMessage);
            }
        });
    }
}
