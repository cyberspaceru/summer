package ru.qa.summer.support.exceptions;

public class PropertyReadingException extends RuntimeException {
    public PropertyReadingException() {
    }

    public PropertyReadingException(String message) {
        super(message);
    }

    public PropertyReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyReadingException(Throwable cause) {
        super(cause);
    }
}
