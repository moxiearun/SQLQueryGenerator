package Exceptions;

/**
 * NoSuchClauseFoundException will notify when invalid clause type is given in query inputs
 */
public class NoSuchClauseFoundException extends Exception{

    private final String exceptionMessage;

    public NoSuchClauseFoundException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
