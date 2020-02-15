package ru.qa.summer.scene.exceptions;

public class TestCaseNotFoundException extends RuntimeException {
    public TestCaseNotFoundException() {
    }

    public TestCaseNotFoundException(String message) {
        super(message);
    }

    public TestCaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestCaseNotFoundException(Throwable cause) {
        super(cause);
    }
}
