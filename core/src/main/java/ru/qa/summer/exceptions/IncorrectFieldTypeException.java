package ru.qa.summer.exceptions;

public class IncorrectFieldTypeException extends RuntimeException {
    public IncorrectFieldTypeException() {
    }

    public IncorrectFieldTypeException(String message) {
        super(message);
    }

    public IncorrectFieldTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectFieldTypeException(Throwable cause) {
        super(cause);
    }

    public IncorrectFieldTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
