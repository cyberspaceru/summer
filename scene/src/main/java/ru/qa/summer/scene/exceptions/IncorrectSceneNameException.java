package ru.qa.summer.scene.exceptions;

public class IncorrectSceneNameException extends RuntimeException {
    public IncorrectSceneNameException() {
    }

    public IncorrectSceneNameException(String message) {
        super(message);
    }

    public IncorrectSceneNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSceneNameException(Throwable cause) {
        super(cause);
    }
}
