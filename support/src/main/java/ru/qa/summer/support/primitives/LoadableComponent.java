package ru.qa.summer.support.primitives;

import ru.qa.summer.support.recovery.RecoveryService;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public interface LoadableComponent<T> {
    default T load() {
        RecoveryService recovery = RecoveryService.create();
        boolean isRecoverable = false;
        if (!recovery.getRules().isEmpty()) {
            isRecoverable = Arrays.stream(Thread.currentThread().getStackTrace())
                    .filter(x -> x.getClassName().equals(LoadableComponent.class.getName()) && x.getMethodName().equals("load"))
                    .count() == 1;
        }
        while (true) {
            AtomicReference<Throwable> holder = new AtomicReference<>();
            long end = System.currentTimeMillis() + waitFor();
            while (System.currentTimeMillis() < end) {
                SimpleEntry<Boolean, T> result = immediately(holder);
                if (result.getKey()) {
                    return result.getValue();
                }
            }
            if (isRecoverable) {
                Class<?> recoveryRule = recovery.canBeRecoveredBy(holder.get());
                if (recoveryRule != null) {
                    System.out.println("Timeout recovered by '" + recoveryRule.getSimpleName() + "' recovery rule");
                    continue;
                }
            }
            break;
        }
        return makeTry();
    }

    default SimpleEntry<Boolean, T> immediately(AtomicReference<Throwable> holder) {
        try {
            return new SimpleEntry<>(true, makeTry());
        } catch (Exception | AssertionError t) {
            holder.set(t);
        }
        return new SimpleEntry<>(false, null);
    }

    long waitFor();

    T makeTry();
}
