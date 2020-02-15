package ru.qa.summer.scene;

public interface Actor<T extends Actor> {
    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }
}
