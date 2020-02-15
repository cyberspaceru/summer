package ru.qa.summer.dg.exceptions;

public class IncorrectArgsNumberException extends RuntimeException {
    public IncorrectArgsNumberException() {
    }

    public IncorrectArgsNumberException(String message) {
        super(message);
    }

    public IncorrectArgsNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectArgsNumberException(Throwable cause) {
        super(cause);
    }
}
