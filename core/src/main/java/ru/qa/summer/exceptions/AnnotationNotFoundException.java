package ru.qa.summer.exceptions;

import static java.lang.String.format;

public class AnnotationNotFoundException extends RuntimeException {
    public AnnotationNotFoundException() {
    }

    public AnnotationNotFoundException(Class aClass) {
        super(format("Annotation '%s' not found", aClass.getSimpleName()));
    }

    public AnnotationNotFoundException(String message) {
        super(message);
    }

    public AnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationNotFoundException(Throwable cause) {
        super(cause);
    }

    public AnnotationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
