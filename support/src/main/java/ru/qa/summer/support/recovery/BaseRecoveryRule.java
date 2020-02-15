package ru.qa.summer.support.recovery;

public abstract class BaseRecoveryRule {
    private final int maxCommonAttempt;
    private final int maxOwnAttempt;

    public BaseRecoveryRule() {
        this(-1, -1);
    }

    /**
     * @param maxCommonAttempt if -1 then infinity attempts
     * @param maxOwnAttempt    if -1 then infinity attempts
     */
    public BaseRecoveryRule(int maxCommonAttempt, int maxOwnAttempt) {
        this.maxCommonAttempt = maxCommonAttempt;
        this.maxOwnAttempt = maxOwnAttempt;
    }

    public abstract boolean test(RecoveryInfo info);

    public boolean isExpired(int currentCommonAttempt, int currentOwnAttempt) {
        return (maxCommonAttempt != -1 && currentCommonAttempt >= maxCommonAttempt) ||
                (maxOwnAttempt != -1 && currentOwnAttempt >= maxOwnAttempt);
    }

    public int getMaxCommonAttempt() {
        return maxCommonAttempt;
    }

    public int getMaxOwnAttempt() {
        return maxOwnAttempt;
    }
}
