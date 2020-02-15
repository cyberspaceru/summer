package ru.qa.summer.context.exceptions;

public class ContextParseException extends RuntimeException {
    public ContextParseException() {
    }

    public ContextParseException(String message) {
        super(message);
    }

    public ContextParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextParseException(Throwable cause) {
        super(cause);
    }

    public ContextParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
