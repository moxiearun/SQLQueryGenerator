package Models;

import java.util.List;

/**
 * Model class to store select query inputs
 */
public class SelectQuery extends Query {
    public boolean isDistinctRequired;
    public boolean isRequiredAllColumns;
    public List<String> columns;
    public List<Clause> clauses;
    public List<Condition> conditions;
}
