package ru.qa.summer.selenium.exceptions;

public class XPathLanguageDuplicationException extends RuntimeException {
    public XPathLanguageDuplicationException() {
    }

    public XPathLanguageDuplicationException(String message) {
        super(message);
    }

    public XPathLanguageDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public XPathLanguageDuplicationException(Throwable cause) {
        super(cause);
    }
}
