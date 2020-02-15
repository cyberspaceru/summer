package ru.qa.summer.exceptions;

public class UndefinedXPathException extends RuntimeException {
    public UndefinedXPathException() {
    }

    public UndefinedXPathException(String message) {
        super(message);
    }

    public UndefinedXPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedXPathException(Throwable cause) {
        super(cause);
    }
}
