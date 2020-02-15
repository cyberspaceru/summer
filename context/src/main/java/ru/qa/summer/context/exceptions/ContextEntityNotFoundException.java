package ru.qa.summer.context.exceptions;

public class ContextEntityNotFoundException extends RuntimeException {
    public ContextEntityNotFoundException() {
    }

    public ContextEntityNotFoundException(String message) {
        super(message);
    }

    public ContextEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextEntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public ContextEntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
