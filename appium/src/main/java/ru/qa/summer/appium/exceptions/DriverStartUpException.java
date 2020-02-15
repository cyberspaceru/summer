package ru.qa.summer.appium.exceptions;

public class DriverStartUpException extends RuntimeException {
    public DriverStartUpException() {
    }

    public DriverStartUpException(String message) {
        super(message);
    }

    public DriverStartUpException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverStartUpException(Throwable cause) {
        super(cause);
    }
}
