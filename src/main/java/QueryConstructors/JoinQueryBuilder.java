package QueryConstructors;

import Exceptions.NoSuchClauseFoundException;
import Helpers.Constants;
import Interfaces.QueryDisplayListener;
import Models.Condition;
import Models.JoinQuery;
import Models.Query;

/**
 * Join query constructor constructs various join queries such as inner join, right join, left join
 * based on the query inputs provided
 */
public class JoinQueryBuilder extends QueryBuilder {

    private final JoinQuery joinQueryInputs;

    public JoinQueryBuilder(Query sqlJoinQueryInputs, QueryDisplayListener queryDisplayListener) {
        this.joinQueryInputs = (JoinQuery) sqlJoinQueryInputs;
        this.queryDisplayListener = queryDisplayListener;
    }

    /**
     * Constructs join queries which will create views from tables.
     */
    @Override
    public void constructQuery() {
        try {
            super.constructQuery();
            sqlQueryBuilder.append(Constants.QUERY_SELECT);
            appendColumnName();
            appendPrimaryTableName();
            appendConditions(getJoinType());
            appendClause(joinQueryInputs.clauses, true);
            sqlQueryBuilder.append(Constants.SEMI_COLON);
            queryDisplayListener.displayConstructedQuery(sqlQueryBuilder.toString());
        } catch (NoSuchClauseFoundException noSuchClauseFoundException) {
            queryDisplayListener.showException(noSuchClauseFoundException.getExceptionMessage());
        }
    }

    /**
     * Appends table name to the query with appropriate prefix (FROM)
     */
    private void appendPrimaryTableName() {
        super.appendTableName(Constants.FROM, joinQueryInputs.primaryTableName);
    }

    /**
     * Appends column names or * to the query
     */
    private void appendColumnName() {
        appendColumnNames(joinQueryInputs.columns, true);
    }

    /**
     * Returns the join type based on join query input
     * @return queryType
     */
    private String getJoinType() {
        if (joinQueryInputs.queryType == null) {
            return null;
        }
        switch (joinQueryInputs.queryType) {
            case Constants.JOIN_TYPE_INNER_JOIN:
                return Constants.JOIN_INNER_JOIN;
            case Constants.JOIN_TYPE_LEFT_JOIN:
                return Constants.JOIN_LEFT_JOIN;
            case Constants.JOIN_TYPE_RIGHT_JOIN:
                return Constants.JOIN_RIGHT_JOIN;
            case Constants.JOIN_TYPE_FULL_JOIN:
                return Constants.JOIN_FULL_JOIN;
        }
        return null;
    }

    /**
     * Appends conditions with operators.
     */
    private void appendConditions(String joinType) {
        if (joinQueryInputs.conditions == null || joinQueryInputs.conditions.size() < 1) {
            return;
        }
        for (Condition condition : joinQueryInputs.conditions) {
            sqlQueryBuilder.append(Constants.SPACE).append(joinType).append(Constants.SPACE)
                    .append(getRightTableName(condition.condition)).append(Constants.SPACE).append(Constants.ON).append(Constants.SPACE)
                    .append(condition.tableName).append(Constants.DOT).append(condition.columnName);
            sqlQueryBuilder.append(Constants.SPACE);
            appendOperator(condition.operatorType);
            sqlQueryBuilder.append(Constants.SPACE);

            if (condition.condition != null) {
                sqlQueryBuilder.append(condition.condition.tableName).append(Constants.DOT)
                        .append(condition.condition.columnName);
            } else {
                appendConditions(condition.operatorType, condition.conditionValues);
            }

        }
    }

    /**
     * Returns secondary table from conditions
     * @param condition
     * @return tableName
     */
    private String getRightTableName(Condition condition) {
        if (condition != null && condition.tableName != null) {
            return condition.tableName;
        }
        return null;
    }
}
