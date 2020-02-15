package ru.qa.summer.exceptions;

public class DuplicateFieldNameException extends RuntimeException {
    public DuplicateFieldNameException() {
    }

    public DuplicateFieldNameException(String message) {
        super(message);
    }

    public DuplicateFieldNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFieldNameException(Throwable cause) {
        super(cause);
    }

    public DuplicateFieldNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
