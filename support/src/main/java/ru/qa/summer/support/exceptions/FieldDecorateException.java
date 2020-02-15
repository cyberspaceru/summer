package ru.qa.summer.support.exceptions;

public class FieldDecorateException extends RuntimeException {
    public FieldDecorateException() {
    }

    public FieldDecorateException(String message) {
        super(message);
    }

    public FieldDecorateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldDecorateException(Throwable cause) {
        super(cause);
    }

    public FieldDecorateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
