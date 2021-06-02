import Helpers.Constants;
import Interfaces.QueryDisplayListener;
import Models.DropQuery;
import Helpers.QueryInputsReader;
import Models.Query;
import Models.SelectQuery;
import QueryConstructors.DropQueryBuilder;
import QueryConstructors.QueryBuilder;
import QueryConstructors.SelectQueryBuilder;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class Main {

    public static void main(String[] arg) {

        // Reads the json file and returns the query inputs as json object
        JSONObject queryInputs =
                new QueryInputsReader("/Users/755774/IdeaProjects/SQLQueryGenerator/src/main/resources/query.json")
                        .readQueryInputs();

        // Converts the json object into objects
        Query query = convertToModels(queryInputs);

        // Constructs the query from objects
        QueryBuilder queryBuilder = null;
        QueryDisplayListener queryDisplayListener = new QueryDisplayListener() {
            @Override
            public void displayConstructedQuery(String query) {
                System.out.println(query);
            }

            @Override
            public void displayException(String exceptionMessage) {
                System.out.println(exceptionMessage);
            }
        };

        if (query instanceof DropQuery) {
            queryBuilder = new DropQueryBuilder(query, queryDisplayListener);
        } else if (query instanceof SelectQuery) {
            queryBuilder = new SelectQueryBuilder(query, queryDisplayListener);
        }
        if (queryBuilder != null) {
            queryBuilder.constructQuery();
        } else {
            System.out.println("Query type not yet supported!");
        }


    }

    public static Query convertToModels(JSONObject queryInputs) {
        Gson gson = new Gson();
        Query queryInputsModel = null;
        Object queryType = queryInputs.get("queryType");
        if (Constants.QUERY_TYPE_SELECT.equals(queryType)) {
            queryInputsModel = gson.fromJson(queryInputs.toString(), SelectQuery.class);
        } else if (Constants.QUERY_TYPE_DROP.equals(queryType)) {
            queryInputsModel = gson.fromJson(queryInputs.toString(), DropQuery.class);
        }
        return queryInputsModel;
    }
}
