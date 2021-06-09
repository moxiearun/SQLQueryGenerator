package Helpers;

public class Constants {
    public static final String ALL_COLUMNS = "*";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String FROM = "FROM";
    public static final String ON = "ON";
    public static final String TABLE = "TABLE";
    public static final String DISTINCT = "DISTINCT";
    public static final String COMMA = ",";
    public static final String SEMI_COLON = ";";

    //Custom exception messages
    public static final String MESSAGE_NO_CLAUSE_FOUND_EXCEPTION = "No such clause is supported! Please verify inputs.";

    //Clauses
    public static final String CLAUSE_WHERE = "WHERE";
    public static final String CLAUSE_ORDER_BY = "ORDER BY";
    public static final String CLAUSE_GROUP_BY = "GROUP BY";
    public static final String CLAUSE_HAVING = "HAVING";

    //Clause types
    public static final String CLAUSE_TYPE_WHERE = "where";
    public static final String CLAUSE_TYPE_ORDER_BY = "order_by";
    public static final String CLAUSE_TYPE_GROUP_BY = "group_by";
    public static final String CLAUSE_TYPE_HAVING = "having";

    //Query type inputs
    public static final String QUERY_TYPE_SELECT = "select";
    public static final String QUERY_TYPE_ALTER = "alter";
    public static final String QUERY_TYPE_DROP = "drop";

    //Query types
    public static final String QUERY_SELECT = "SELECT";
    public static final String QUERY_ALTER = "ALTER";
    public static final String QUERY_DROP = "DROP";

    // Operators
    public static final String OPERATOR_EQUAL_TO = "=";
    public static final String OPERATOR_NOT_EQUAL_TO = "<>";
    public static final String OPERATOR_GREATER_THAN_EQUAL_TO = ">=";
    public static final String OPERATOR_GREATER_THAN = ">";
    public static final String OPERATOR_LESSER_THAN_EQUAL_TO = "<=";
    public static final String OPERATOR_LESSER_THAN = "<";
    public static final String OPERATOR_LIKE = "LIKE";
    public static final String OPERATOR_IN = "IN";
    public static final String OPERATOR_BETWEEN = "BETWEEN";
    public static final String OPERATOR_OR = "OR";
    public static final String OPERATOR_AND = "AND";

    // Operator types
    public static final String OPERATOR_TYPE_EQUAL_TO = "equal_to";
    public static final String OPERATOR_TYPE_NOT_EQUAL_TO = "not_equal_to";
    public static final String OPERATOR_TYPE_GREATER_THAN_EQUAL_TO = "greater_than_equal_to";
    public static final String OPERATOR_TYPE_GREATER_THAN = "greater_than";
    public static final String OPERATOR_TYPE_LESSER_THAN_EQUAL_TO = "lesser_than_equal_to";
    public static final String OPERATOR_TYPE_LESSER_THAN = "lesser_than";
    public static final String OPERATOR_TYPE_LIKE = "like";
    public static final String OPERATOR_TYPE_IN = "in";
    public static final String OPERATOR_TYPE_BETWEEN = "between";
    public static final String OPERATOR_TYPE_OR = "or";
    public static final String OPERATOR_TYPE_AND = "and";

    // Join types
    public static final String JOIN_TYPE_INNER_JOIN = "inner_join";
    public static final String JOIN_TYPE_LEFT_JOIN = "left_join";
    public static final String JOIN_TYPE_RIGHT_JOIN = "right_join";
    public static final String JOIN_TYPE_FULL_JOIN = "full_join";

    // Joins
    public static final String JOIN_INNER_JOIN = "INNER JOIN";
    public static final String JOIN_LEFT_JOIN = "LEFT JOIN";
    public static final String JOIN_RIGHT_JOIN = "RIGHT JOIN";
    public static final String JOIN_FULL_JOIN = "FULL JOIN";

}
