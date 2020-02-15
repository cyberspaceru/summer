package ru.qa.summer.context.exceptions;

public class ContextValueException extends RuntimeException {
    public ContextValueException() {
    }

    public ContextValueException(String message) {
        super(message);
    }

    public ContextValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextValueException(Throwable cause) {
        super(cause);
    }

    public ContextValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
