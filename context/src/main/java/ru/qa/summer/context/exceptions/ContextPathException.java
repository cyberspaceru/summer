package ru.qa.summer.context.exceptions;

public class ContextPathException extends RuntimeException {
    public ContextPathException() {
    }

    public ContextPathException(String message) {
        super(message);
    }

    public ContextPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextPathException(Throwable cause) {
        super(cause);
    }

    public ContextPathException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
