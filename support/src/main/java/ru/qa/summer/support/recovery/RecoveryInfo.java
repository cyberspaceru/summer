package ru.qa.summer.support.recovery;

public class RecoveryInfo {
    private int commonAttempts;
    private int ownAttempts;
    private Throwable throwable;

    public int getCommonAttempts() {
        return commonAttempts;
    }

    public void setCommonAttempts(int commonAttempts) {
        this.commonAttempts = commonAttempts;
    }

    public int getOwnAttempts() {
        return ownAttempts;
    }

    public void setOwnAttempts(int ownAttempts) {
        this.ownAttempts = ownAttempts;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
