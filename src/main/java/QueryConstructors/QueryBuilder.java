package QueryConstructors;

import Helpers.Constants;
import Interfaces.QueryDisplayListener;

import java.util.Iterator;
import java.util.List;

/**
 * Common query constructor contains common implementations.
 */
public class QueryBuilder {

    public QueryDisplayListener queryDisplayListener;
    public StringBuilder sqlQueryBuilder;

    public void constructQuery() {
        sqlQueryBuilder = new StringBuilder();
    }

    /**
     * Appends table name to the query with appropriate prefix (FROM/TABLE)
     */
    public void appendTable(String tablePrefix, String tableName) {
        sqlQueryBuilder.append(Constants.SPACE).append(tablePrefix).append(Constants.SPACE).append(tableName);
    }

    /**
     * Appends column names when its specifically mentioned in query inputs.
     *
     * @param list list of columns to displayed or considered in clause operations.
     */
    public void iterateList(List<String> list) {
        Iterator<String> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            sqlQueryBuilder.append(Constants.SPACE).append(listIterator.next());
            if (!listIterator.hasNext()) {
                //Avoiding comma for last column name
                break;
            }
            sqlQueryBuilder.append(Constants.COMMA);
        }
    }
}
