package ru.qa.summer.support.util;

public class BenchmarkUtil {
    private BenchmarkUtil() {
    }

    public static long simple(Runnable runnable) {
        return simple(runnable, 1);
    }

    public static long simple(Runnable runnable, long count) {
        long start = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            runnable.run();
        }
        return System.currentTimeMillis() - start;
    }

    public static long simpleAverage(Runnable runnable, long count) {
        return simple(runnable, count) / count;
    }
}
