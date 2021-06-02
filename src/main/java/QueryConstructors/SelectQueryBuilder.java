package QueryConstructors;

import Exceptions.NoSuchClauseFoundException;
import Helpers.Constants;
import Interfaces.QueryDisplayListener;
import Models.Clause;
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
            appendClause();
            appendConditions();
            sqlQueryBuilder.append(Constants.SEMI_COLON);
            queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());

        } catch (NoSuchClauseFoundException noSuchClauseFoundException) {
            queryDisplayListener.displayException(noSuchClauseFoundException.getExceptionMessage());
        }
    }

    /**
     * Appends column names or * to the query
     */
    private void appendColumnName() {
        if (sqlSelectQueryInputs.isRequiredAllColumns) {
            sqlQueryBuilder.append(Constants.SPACE).append(Constants.ALL_COLUMNS);
        } else {
            iterateList(sqlSelectQueryInputs.columns);
        }
    }

    /**
     * Appends table name to the query with appropriate prefix (FROM)
     */
    private void appendTableName() {
        super.appendTable(Constants.FROM, sqlSelectQueryInputs.tableName);
    }

    /**
     * Appends clauses along with column names.
     *
     * @throws NoSuchClauseFoundException can be thrown when invalid clause type found in query inputs.
     */
    private void appendClause() throws NoSuchClauseFoundException {
        if (sqlSelectQueryInputs.clauses == null || sqlSelectQueryInputs.clauses.size() < 1) {
            return;
        }
        for (Clause clause : sqlSelectQueryInputs.clauses) {
            sqlQueryBuilder.append(Constants.SPACE);
            switch (clause.clauseType) {
                case Constants.CLAUSE_TYPE_WHERE:
                    sqlQueryBuilder.append(Constants.CLAUSE_WHERE);
                    break;
                case Constants.CLAUSE_TYPE_ORDER_BY:
                    sqlQueryBuilder.append(Constants.CLAUSE_ORDER_BY);
                    iterateList(clause.columns);
                    break;
                case Constants.CLAUSE_TYPE_GROUP_BY:
                    sqlQueryBuilder.append(Constants.CLAUSE_GROUP_BY);
                    iterateList(clause.columns);
                    break;
                case Constants.CLAUSE_TYPE_HAVING:
                    sqlQueryBuilder.append(Constants.CLAUSE_HAVING);
                    break;
                default:
                    throw new NoSuchClauseFoundException(Constants.MESSAGE_NO_CLAUSE_FOUND_EXCEPTION);
            }
        }
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
            switch (condition.operatorType) {
                case Constants.OPERATOR_TYPE_EQUAL_TO:
                    sqlQueryBuilder.append(Constants.OPERATOR_EQUAL_TO);
                    break;
                case Constants.OPERATOR_TYPE_NOT_EQUAL_TO:
                    sqlQueryBuilder.append(Constants.OPERATOR_NOT_EQUAL_TO);
                    break;
                case Constants.OPERATOR_TYPE_GREATER_THAN:
                    sqlQueryBuilder.append(Constants.OPERATOR_GREATER_THAN);
                    break;
                case Constants.OPERATOR_TYPE_GREATER_THAN_EQUAL_TO:
                    sqlQueryBuilder.append(Constants.OPERATOR_GREATER_THAN_EQUAL_TO);
                    break;
                case Constants.OPERATOR_TYPE_LESSER_THAN_EQUAL_TO:
                    sqlQueryBuilder.append(Constants.OPERATOR_LESSER_THAN_EQUAL_TO);
                    break;
                case Constants.OPERATOR_TYPE_LESSER_THAN:
                    sqlQueryBuilder.append(Constants.OPERATOR_LESSER_THAN);
                    break;
                case Constants.OPERATOR_TYPE_LIKE:
                    sqlQueryBuilder.append(Constants.OPERATOR_LIKE);
                    break;
                case Constants.OPERATOR_TYPE_IN:
                    sqlQueryBuilder.append(Constants.OPERATOR_IN);
                    break;
                case Constants.OPERATOR_TYPE_BETWEEN:
                    sqlQueryBuilder.append(Constants.OPERATOR_BETWEEN);
                    break;
            }
            sqlQueryBuilder.append(Constants.SPACE);
            if (condition.operatorType.equals(Constants.OPERATOR_TYPE_LIKE)
                    || condition.operatorType.equals(Constants.OPERATOR_TYPE_IN)) {
                iterateList(condition.conditionValues);
            } else if (condition.operatorType.equals(Constants.OPERATOR_TYPE_BETWEEN) && condition.conditionValues.size() == 2) {
                sqlQueryBuilder.append(condition.conditionValues.get(0)).append(Constants.SPACE).append("AND")
                        .append(Constants.SPACE).append(condition.conditionValues.get(1));
            } else if (condition.conditionValues != null && condition.conditionValues.get(0) != null) {
                sqlQueryBuilder.append(condition.conditionValues.get(0));
            }
        }
    }
}
