package Models;

import java.util.List;

/**
 * Model class to store query inputs
 */
public class Query {
    public String queryType;
    public String tableName;
    public boolean isDistinctRequired;
    public boolean isRequiredAllColumns;
    public List<String> columns;
    public List<Clause> clauses;
    public List<Condition> conditions;
}
