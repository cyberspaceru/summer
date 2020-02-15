package ru.qa.summer.scene.exceptions;

public class IncorrectSceneClassLocationException extends RuntimeException {
    public IncorrectSceneClassLocationException() {
        super();
    }

    public IncorrectSceneClassLocationException(String message) {
        super(message);
    }

    public IncorrectSceneClassLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSceneClassLocationException(Throwable cause) {
        super(cause);
    }
}
