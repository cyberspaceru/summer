package ru.qa.summer.selenium.fluents;

import java.util.function.Consumer;
import java.util.function.Function;

public class Fluent<T> {
    private final T object;
    private final boolean isFailed;

    public Fluent(T object, boolean isFailed) {
        this.object = object;
        this.isFailed = isFailed;
    }

    public <K> FluentOr<K> orThen(Function<T, Fluent<K>> function) {
        return new FluentOr<>(object, isFailed).orThen(function);
    }

    public void or(Consumer<T> consumer) {
        new FluentOr<>(object, isFailed).or(consumer);
    }

    public <K> FluentAnd<K> andThen(Function<T, Fluent<K>> function) {
        return new FluentAnd<>(object, isFailed).andThen(function);
    }

    public void and(Consumer<T> consumer) {
        new FluentAnd<>(object, isFailed).and(consumer);
    }

    public T getObject() {
        return object;
    }

    public boolean isFailed() {
        return isFailed;
    }
}
