package ru.qa.summer.support.exceptions;

public class NameParseException extends RuntimeException {
    public NameParseException() {
    }

    public NameParseException(String message) {
        super(message);
    }

    public NameParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameParseException(Throwable cause) {
        super(cause);
    }

    public NameParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
