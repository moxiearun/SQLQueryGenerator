package QueryConstructors;

import Helpers.Constants;
import Interfaces.QueryDisplayListener;
import Models.DropQuery;
import Models.Query;

/**
 * Drop query constructor constructs drop SQL query based on the query inputs provided
 */
public class DropQueryBuilder extends QueryBuilder {

    private final DropQuery dropQueryInputs;


    public DropQueryBuilder(Query sqlSelectQueryInputs, QueryDisplayListener queryDisplayListener) {
        this.dropQueryInputs = (DropQuery) sqlSelectQueryInputs;
        this.queryDisplayListener = queryDisplayListener;
    }

    /**
     * Constructs drop query which will drop the table from database.
     */
    public void constructQuery() {
        super.constructQuery();
        sqlQueryBuilder.append(Constants.QUERY_DROP);
        appendTableName();
        sqlQueryBuilder.append(Constants.SEMI_COLON);
        queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());
    }

    /**
     * Appends table name to the query with appropriate prefix (TABLE)
     */
    private void appendTableName() {
        super.appendTable(Constants.TABLE, dropQueryInputs.tableName);
    }
}
