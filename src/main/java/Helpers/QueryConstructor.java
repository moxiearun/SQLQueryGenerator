package Helpers;

import Interfaces.QueryDisplayListener;
import Models.Condition;
import Models.Query;

import java.util.Iterator;
import java.util.List;

public class QueryConstructor {

    private final Query sqlQueryInputs;
    private QueryDisplayListener queryDisplayListener;

    public QueryConstructor(Query sqlQueryInputs) {
        this.sqlQueryInputs = sqlQueryInputs;
    }

    public void constructQuery(QueryDisplayListener queryDisplayListener) {
    this.queryDisplayListener = queryDisplayListener;
        switch (sqlQueryInputs.queryType) {
            case "select":
                constructSelectQuery();
                break;
            case "alter":
                constructAlterQuery();
                break;
            case "drop":
                constructDropQuery();
                break;
        }
    }

    private void constructSelectQuery() {
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
    }

    private void constructAlterQuery() {
        StringBuilder sqlQueryBuilder = new StringBuilder();
        sqlQueryBuilder.append(Constants.QUERY_TYPE_ALTER);
        appendTableName(sqlQueryBuilder);
        appendClause(sqlQueryBuilder);
        appendConditions(sqlQueryBuilder);
        queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());
    }

    private void constructDropQuery() {
        StringBuilder sqlQueryBuilder = new StringBuilder();
        sqlQueryBuilder.append(Constants.QUERY_TYPE_DROP);
        appendTableName(sqlQueryBuilder);
        appendClause(sqlQueryBuilder);
        appendConditions(sqlQueryBuilder);
        queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());
    }

    private void appendColumnName(StringBuilder sqlQueryBuilder) {

        if (sqlQueryInputs.isRequiredAllColumns) {
            sqlQueryBuilder.append(Constants.SPACE).append(Constants.ALL_COLUMNS);
        } else {
            iterateColumns(sqlQueryInputs.columns, sqlQueryBuilder);
        }
    }

    private void appendTableName(StringBuilder sqlQueryBuilder) {
        sqlQueryBuilder.append(Constants.SPACE);
        if (sqlQueryInputs.queryType.equals(Constants.QUERY_TYPE_SELECT.toLowerCase())) {
            sqlQueryBuilder.append(Constants.FROM);
        } else {
            sqlQueryBuilder.append(Constants.TABLE);
        }
        sqlQueryBuilder.append(Constants.SPACE).append(sqlQueryInputs.tableName);
    }

    private void appendClause(StringBuilder sqlQueryBuilder) {
        switch (sqlQueryInputs.clause.clauseType) {
            case Constants.CLAUSE_TYPE_WHERE:
                sqlQueryBuilder.append(Constants.SPACE).append(Constants.CLAUSE_WHERE);
                break;
            case Constants.CLAUSE_TYPE_ORDER_BY:
                sqlQueryBuilder.append(Constants.SPACE).append(Constants.CLAUSE_ORDER_BY);
                iterateColumns(sqlQueryInputs.clause.columns, sqlQueryBuilder);
                break;
        }
    }

    private void appendConditions(StringBuilder sqlQueryBuilder) {
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
