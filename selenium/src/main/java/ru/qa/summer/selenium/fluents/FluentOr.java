package ru.qa.summer.selenium.fluents;

import java.util.function.Consumer;
import java.util.function.Function;

public class FluentOr<T> {
    private final T object;
    private final boolean isFailed;

    public FluentOr(Fluent<T> fluent) {
        this.object = fluent.getObject();
        this.isFailed = fluent.isFailed();
    }

    public FluentOr(T object, boolean isFailed) {
        this.object = object;
        this.isFailed = isFailed;
    }

    public <K> FluentOr<K> orThen(Function<T, Fluent<K>> function) {
        if (isFailed && object != null) {
            return new FluentOr<>(function.apply(object));
        }
        return new FluentOr<>(null, false);
    }

    public void or(Consumer<T> consumer) {
        if (isFailed && object != null) {
            consumer.accept(object);
        }
    }
}
