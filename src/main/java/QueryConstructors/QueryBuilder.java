package QueryConstructors;

import Exceptions.NoSuchClauseFoundException;
import Helpers.Constants;
import Interfaces.QueryDisplayListener;
import Models.Clause;
import Models.Column;
import com.sun.tools.internal.jxc.ap.Const;

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
    public void appendTableName(String tablePrefix, String tableName) {
        sqlQueryBuilder.append(Constants.SPACE);
        if (tablePrefix != null)
            sqlQueryBuilder.append(tablePrefix).append(Constants.SPACE);
        sqlQueryBuilder.append(tableName);
    }

    /**
     * Appends column names when its specifically mentioned in query inputs.
     *
     * @param list list of columns to displayed or considered in clause operations.
     */
    public void appendColumnNames(List<Column> list, boolean isTableNameRequired) {
        Iterator<Column> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            Column column = listIterator.next();
            sqlQueryBuilder.append(Constants.SPACE);
            if (isTableNameRequired) {
                sqlQueryBuilder.append(column.tableName).append(Constants.DOT);
            }
            sqlQueryBuilder.append(column.column);
            if (!listIterator.hasNext()) {
                //Avoiding comma for last column name
                break;
            }
            sqlQueryBuilder.append(Constants.COMMA);
        }
    }

    /**
     * Appends the operator to query
     * @param operatorType
     */
    public void appendOperator(String operatorType){
        switch (operatorType) {
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
    }

    /**
     * Append RHS part of conditions to query
     * @param operatorType
     * @param conditionValues
     */
    public void appendConditions(String operatorType, List<String> conditionValues) {
        if (operatorType.equals(Constants.OPERATOR_TYPE_LIKE) || operatorType.equals(Constants.OPERATOR_TYPE_IN)) {
            appendConditions(conditionValues);
        } else if (operatorType.equals(Constants.OPERATOR_TYPE_BETWEEN) && conditionValues.size() == 2) {
            sqlQueryBuilder.append(conditionValues.get(0)).append(Constants.SPACE).append(Constants.OPERATOR_AND)
                    .append(Constants.SPACE).append(conditionValues.get(1));
        } else if (conditionValues != null && conditionValues.get(0) != null) {
            sqlQueryBuilder.append(conditionValues.get(0));
        }
    }

    /**
     * Appends the multiple condition values to query
     * @param conditionValues
     */
    private void appendConditions(List<String> conditionValues) {
        Iterator<String> conditionValuesIterator = conditionValues.iterator();
        while (conditionValuesIterator.hasNext()) {
            sqlQueryBuilder.append(Constants.SPACE).append(conditionValuesIterator.next());
            if (!conditionValuesIterator.hasNext()) {
                //Avoiding comma for last column name
                break;
            }
            sqlQueryBuilder.append(Constants.COMMA);
        }
    }

    /**
     * Appends clauses along with column names.
     *
     * @throws NoSuchClauseFoundException can be thrown when invalid clause type found in query inputs.
     */
    public void appendClause(List<Clause> clauses, boolean isTableNameRequired) throws NoSuchClauseFoundException {
        if (clauses == null || clauses.size() < 1) {
            return;
        }
        for (Clause clause : clauses) {
            sqlQueryBuilder.append(Constants.SPACE);
            switch (clause.clauseType) {
                case Constants.CLAUSE_TYPE_WHERE:
                    sqlQueryBuilder.append(Constants.CLAUSE_WHERE);
                    break;
                case Constants.CLAUSE_TYPE_ORDER_BY:
                    sqlQueryBuilder.append(Constants.CLAUSE_ORDER_BY);
                    appendColumnNames(clause.columns, isTableNameRequired);
                    break;
                case Constants.CLAUSE_TYPE_GROUP_BY:
                    sqlQueryBuilder.append(Constants.CLAUSE_GROUP_BY);
                    appendColumnNames(clause.columns, isTableNameRequired);
                    break;
                case Constants.CLAUSE_TYPE_HAVING:
                    sqlQueryBuilder.append(Constants.CLAUSE_HAVING);
                    break;
                default:
                    throw new NoSuchClauseFoundException(Constants.MESSAGE_NO_CLAUSE_FOUND_EXCEPTION);
            }
        }
    }

    public void appendConditionOperators(String conditionOperator) {
        if (conditionOperator.equals(Constants.OPERATOR_TYPE_AND)) {
             sqlQueryBuilder.append(Constants.SPACE).append(Constants.OPERATOR_AND);
        } else if (conditionOperator.equals(Constants.OPERATOR_TYPE_OR)) {
            sqlQueryBuilder.append(Constants.SPACE).append(Constants.OPERATOR_OR);
        }
    }
}
