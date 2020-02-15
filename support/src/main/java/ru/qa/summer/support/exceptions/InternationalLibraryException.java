package ru.qa.summer.support.exceptions;

public class InternationalLibraryException extends RuntimeException {
    public InternationalLibraryException() {
    }

    public InternationalLibraryException(String message) {
        super(message);
    }

    public InternationalLibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternationalLibraryException(Throwable cause) {
        super(cause);
    }
}
