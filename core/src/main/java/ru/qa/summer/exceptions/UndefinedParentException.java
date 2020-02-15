package ru.qa.summer.exceptions;

public class UndefinedParentException extends RuntimeException {
    public UndefinedParentException() {
    }

    public UndefinedParentException(String message) {
        super(message);
    }

    public UndefinedParentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedParentException(Throwable cause) {
        super(cause);
    }

    public UndefinedParentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}