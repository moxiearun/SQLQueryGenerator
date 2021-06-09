package QueryConstructors;

import Exceptions.NoSuchClauseFoundException;
import Helpers.Constants;
import Interfaces.QueryDisplayListener;
import Models.Condition;
import Models.Query;
import Models.SelectQuery;

/**
 * QueryConstructor construct SQL query based on the query inputs provided
 */
public class SelectQueryBuilder extends QueryBuilder {

    private final SelectQuery sqlSelectQueryInputs;

    public SelectQueryBuilder(Query sqlSelectQueryInputs, QueryDisplayListener queryDisplayListener) {
        this.sqlSelectQueryInputs = (SelectQuery) sqlSelectQueryInputs;
        this.queryDisplayListener = queryDisplayListener;
    }

    /**
     * Constructs select queries which will fetch data from table.
     */
    @Override
    public void constructQuery() {
        try {
            super.constructQuery();
            sqlQueryBuilder.append(Constants.QUERY_SELECT);
            // Add distinct if needed
            if (sqlSelectQueryInputs.isDistinctRequired) {
                sqlQueryBuilder.append(Constants.SPACE).append(Constants.DISTINCT);
            }
            appendColumnName();
            appendTableName();
            appendClause(sqlSelectQueryInputs.clauses);
            appendConditions();
            sqlQueryBuilder.append(Constants.SEMI_COLON);
            queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());

        } catch (NoSuchClauseFoundException noSuchClauseFoundException) {
            queryDisplayListener.showException(noSuchClauseFoundException.getExceptionMessage());
        }
    }

    /**
     * Appends column names or * to the query
     */
    private void appendColumnName() {
        if (sqlSelectQueryInputs.isRequiredAllColumns) {
            sqlQueryBuilder.append(Constants.SPACE).append(Constants.ALL_COLUMNS);
        } else {
            appendColumnNames(sqlSelectQueryInputs.columns, false);
        }
    }

    /**
     * Appends table name to the query with appropriate prefix (FROM)
     */
    private void appendTableName() {
        super.appendTableName(Constants.FROM, sqlSelectQueryInputs.primaryTableName);
    }

    /**
     * Appends conditions with operators.
     */
    private void appendConditions() {
        if (sqlSelectQueryInputs.conditions == null || sqlSelectQueryInputs.conditions.size() < 1) {
            return;
        }
        for (Condition condition : sqlSelectQueryInputs.conditions) {
            sqlQueryBuilder.append(Constants.SPACE).append(condition.columnName);
            sqlQueryBuilder.append(Constants.SPACE);
            appendOperator(condition.operatorType);
            sqlQueryBuilder.append(Constants.SPACE);
            appendConditions(condition.operatorType, condition.conditionValues);
        }
    }
}
