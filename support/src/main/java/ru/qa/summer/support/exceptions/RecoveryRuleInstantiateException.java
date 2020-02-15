package ru.qa.summer.support.exceptions;

public class RecoveryRuleInstantiateException extends RuntimeException {
    public RecoveryRuleInstantiateException() {
    }

    public RecoveryRuleInstantiateException(String message) {
        super(message);
    }

    public RecoveryRuleInstantiateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecoveryRuleInstantiateException(Throwable cause) {
        super(cause);
    }
}
