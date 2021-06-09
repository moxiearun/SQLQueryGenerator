package Models;

import java.util.List;

/**
 * Test conditions to be added in the SQL query
 */
public class Condition {
    public String columnName;
    public String operatorType;
    public List<String> conditionValues;
    public String tableName;
    public String conditionOperator;
    public Condition condition;
}
