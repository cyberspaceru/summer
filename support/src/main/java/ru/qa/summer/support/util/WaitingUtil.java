package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import ru.qa.summer.support.exceptions.SleepException;
import ru.qa.summer.support.primitives.LoadableComponent;

import java.time.Duration;
import java.util.function.Supplier;

import static ru.qa.summer.support.SupportConfiguration.SUPPORT_CONFIGURATION;

@UtilityClass
public class WaitingUtil {
    public static void sleepForSeconds(long seconds) {
        sleepForMillis(Duration.ofSeconds(seconds).toMillis());
    }

    public static void sleepForMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new SleepException();
        }
    }

    public static LoadableSpecification loadable(Runnable runnable) {
        return new LoadableSpecification(runnable);
    }

    public static class LoadableSpecification {
        private final Runnable runnable;
        private long timeout;

        public LoadableSpecification(Runnable runnable, long timeout) {
            this.runnable = runnable;
            this.timeout = timeout;
        }

        public LoadableSpecification(Runnable runnable) {
            this(runnable, SUPPORT_CONFIGURATION.getLoadableTimeout());
        }

        public LoadableSpecification setTimeout(long millis) {
            this.timeout = millis;
            return this;
        }

        public LoadableSpecification setTimeoutInSeconds(long seconds) {
            return setTimeout(Duration.ofSeconds(seconds).toMillis());
        }

        public void orThrow(Supplier<RuntimeException> supplier) {
            try {
                execute();
            } catch (Exception | AssertionError t) {
                throw supplier.get();
            }
        }

        public void orThrow(RuntimeException e) {
            try {
                execute();
            } catch (Exception | AssertionError t) {
                throw e;
            }
        }

        public void execute() {
            new LoadableComponent<ObjectUtils.Null>() {
                @Override
                public long waitFor() {
                    return timeout;
                }

                @Override
                public ObjectUtils.Null makeTry() {
                    runnable.run();
                    return ObjectUtils.NULL;
                }
            }.load();
        }
    }
}
