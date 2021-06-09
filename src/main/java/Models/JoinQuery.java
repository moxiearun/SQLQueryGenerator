package Models;

import java.util.List;

/**
 * Model class to store join query inputs
 */
public class JoinQuery extends Query{

    public boolean isDistinctRequired;
    public List<Column> columns;
    public List<Clause> clauses;
    public List<Condition> conditions;

}
