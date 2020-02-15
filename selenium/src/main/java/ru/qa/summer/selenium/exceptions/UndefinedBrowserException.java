package ru.qa.summer.selenium.exceptions;

public class UndefinedBrowserException extends RuntimeException {
    public UndefinedBrowserException() {
    }

    public UndefinedBrowserException(String message) {
        super(message);
    }

    public UndefinedBrowserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedBrowserException(Throwable cause) {
        super(cause);
    }
}
