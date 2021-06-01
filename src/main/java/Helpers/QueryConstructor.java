package Helpers;

import Exceptions.NoSuchClauseFoundException;
import Interfaces.QueryDisplayListener;
import Models.Clause;
import Models.Condition;
import Models.Query;

import java.util.Iterator;
import java.util.List;

/**
 * QueryConstructor construct SQL query based on the query inputs provided
 */
public class QueryConstructor {

    private final Query sqlQueryInputs;
    private QueryDisplayListener queryDisplayListener;

    public QueryConstructor(Query sqlQueryInputs) {
        this.sqlQueryInputs = sqlQueryInputs;
    }

    /**
     * Identifies query type.
     * @param queryDisplayListener to display query or exception messages if encountered.
     */
    public void constructQuery(QueryDisplayListener queryDisplayListener) {
        this.queryDisplayListener = queryDisplayListener;
        switch (sqlQueryInputs.queryType) {
            case "select":
                constructSelectQuery();
                break;
            case "drop":
                constructDropQuery();
                break;
        }
    }

    /**
     * Constructs select queries which will fetch data from table.
     */
    private void constructSelectQuery() {
        try {

            StringBuilder sqlQueryBuilder = new StringBuilder();
            sqlQueryBuilder.append(Constants.QUERY_TYPE_SELECT);
            // Add distinct if needed
            if (sqlQueryInputs.isDistinctRequired) {
                sqlQueryBuilder.append(Constants.SPACE).append(Constants.DISTINCT);
            }
            appendColumnName(sqlQueryBuilder);
            appendTableName(sqlQueryBuilder);
            appendClause(sqlQueryBuilder);
            appendConditions(sqlQueryBuilder);
            queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());

        } catch (NoSuchClauseFoundException noSuchClauseFoundException) {
            queryDisplayListener.displayException(noSuchClauseFoundException.getExceptionMessage());
        }
    }

    /**
     * Constructs drop query which will drop the table from database.
     */
    private void constructDropQuery() {

        StringBuilder sqlQueryBuilder = new StringBuilder();
        sqlQueryBuilder.append(Constants.QUERY_TYPE_DROP);
        appendTableName(sqlQueryBuilder);
        queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());
    }

    /**
     * Appends column names or * to the query
     */
    private void appendColumnName(StringBuilder sqlQueryBuilder) {

        if (sqlQueryInputs.isRequiredAllColumns) {
            sqlQueryBuilder.append(Constants.SPACE).append(Constants.ALL_COLUMNS);
        } else {
            iterateColumns(sqlQueryInputs.columns, sqlQueryBuilder);
        }
    }

    /**
     * Appends table name to the query with appropriate prefix (FROM/TABLE)
     */
    private void appendTableName(StringBuilder sqlQueryBuilder) {
        sqlQueryBuilder.append(Constants.SPACE);
        if (sqlQueryInputs.queryType.equals(Constants.QUERY_TYPE_SELECT.toLowerCase())) {
            sqlQueryBuilder.append(Constants.FROM);
        } else {
            sqlQueryBuilder.append(Constants.TABLE);
        }
        sqlQueryBuilder.append(Constants.SPACE).append(sqlQueryInputs.tableName);
    }

    /**
     * Appends clauses along with column names.
     * @throws NoSuchClauseFoundException can be thrown when invalid clause type found in query inputs.
     */
    private void appendClause(StringBuilder sqlQueryBuilder) throws NoSuchClauseFoundException {
        if (sqlQueryInputs.clauses == null || sqlQueryInputs.clauses.size() < 1) {
            return;
        }
        for (Clause clause : sqlQueryInputs.clauses) {
            sqlQueryBuilder.append(Constants.SPACE);
            switch (clause.clauseType) {
                case Constants.CLAUSE_TYPE_WHERE:
                    sqlQueryBuilder.append(Constants.CLAUSE_WHERE);
                    break;
                case Constants.CLAUSE_TYPE_ORDER_BY:
                    sqlQueryBuilder.append(Constants.CLAUSE_ORDER_BY);
                    iterateColumns(clause.columns, sqlQueryBuilder);
                    break;
                case Constants.CLAUSE_TYPE_GROUP_BY:
                    sqlQueryBuilder.append(Constants.CLAUSE_GROUP_BY);
                    iterateColumns(clause.columns, sqlQueryBuilder);
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
    private void appendConditions(StringBuilder sqlQueryBuilder) {
        if (sqlQueryInputs.conditions == null || sqlQueryInputs.conditions.size() < 1) {
            return;
        }
        for (Condition condition : sqlQueryInputs.conditions) {
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
            sqlQueryBuilder.append(condition.conditionValue);
        }
    }

    /**
     * Appends column names when its specifically mentioned in query inputs.
     * @param columns list of columns to displayed or considered in clause operations.
     */
    private void iterateColumns(List<String> columns, StringBuilder sqlQueryBuilder) {
        Iterator<String> columnNameIterator = columns.iterator();
        while (columnNameIterator.hasNext()) {
            sqlQueryBuilder.append(Constants.SPACE).append(columnNameIterator.next());
            if (!columnNameIterator.hasNext()) {
                //Avoiding comma for last column name
                break;
            }
            sqlQueryBuilder.append(",");
        }
    }
}
