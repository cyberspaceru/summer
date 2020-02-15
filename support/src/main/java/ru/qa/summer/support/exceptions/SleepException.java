package ru.qa.summer.support.exceptions;

public class SleepException extends RuntimeException {
    public SleepException() {
    }

    public SleepException(String message) {
        super(message);
    }

    public SleepException(String message, Throwable cause) {
        super(message, cause);
    }

    public SleepException(Throwable cause) {
        super(cause);
    }
}
