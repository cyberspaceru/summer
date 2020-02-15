package ru.qa.summer.exceptions;

public class MethodExecutionException extends RuntimeException {
    public MethodExecutionException() {
    }

    public MethodExecutionException(String message) {
        super(message);
    }

    public MethodExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodExecutionException(Throwable cause) {
        super(cause);
    }

    public MethodExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
