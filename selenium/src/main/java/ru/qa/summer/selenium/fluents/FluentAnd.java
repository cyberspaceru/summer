package ru.qa.summer.selenium.fluents;

import java.util.function.Consumer;
import java.util.function.Function;

public class FluentAnd<T> {
    private final T object;
    private final boolean isFailed;

    public FluentAnd(Fluent<T> fluent) {
        this.object = fluent.getObject();
        this.isFailed = fluent.isFailed();
    }

    public FluentAnd(T object, boolean isFailed) {
        this.object = object;
        this.isFailed = isFailed;
    }

    public <K> FluentAnd<K> andThen(Function<T, Fluent<K>> function) {
        if (!isFailed && object != null) {
            return new FluentAnd<>(function.apply(object));
        }
        return new FluentAnd<>(null, false);
    }

    public void and(Consumer<T> consumer) {
        if (!isFailed && object != null) {
            consumer.accept(object);
        }
    }
}
