package Exceptions;

public class NoSuchClauseFoundException extends Exception{

    private String exceptionMessage;
    public NoSuchClauseFoundException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
