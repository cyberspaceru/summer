package ru.qa.summer.exceptions;

public class InstantiateException extends RuntimeException {
    public InstantiateException() {
    }

    public InstantiateException(String message) {
        super(message);
    }

    public InstantiateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstantiateException(Throwable cause) {
        super(cause);
    }

    public InstantiateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
