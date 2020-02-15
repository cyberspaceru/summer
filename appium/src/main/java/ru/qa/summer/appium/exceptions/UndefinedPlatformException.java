package ru.qa.summer.appium.exceptions;

public class UndefinedPlatformException extends RuntimeException {
    public UndefinedPlatformException() {
    }

    public UndefinedPlatformException(String message) {
        super(message);
    }

    public UndefinedPlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedPlatformException(Throwable cause) {
        super(cause);
    }
}
