package ru.qa.summer.selenium.exceptions;

public class StandNotFoundException extends RuntimeException {
    public StandNotFoundException() {
    }

    public StandNotFoundException(String message) {
        super(message);
    }

    public StandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StandNotFoundException(Throwable cause) {
        super(cause);
    }
}
