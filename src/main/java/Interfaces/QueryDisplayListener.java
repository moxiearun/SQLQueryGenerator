package Interfaces;

public interface QueryDisplayListener {

    /**
     * Displays the constructed query
     *
     * @param query constructed query by QueryConstructor
     */
    public void displayConstructedQuery(String query);

    /**
     * Displays the exception message if any exception encountered while constructing query.
     *
     * @param exceptionMessage Exception specific message
     */
    public void displayException(String exceptionMessage);

}
