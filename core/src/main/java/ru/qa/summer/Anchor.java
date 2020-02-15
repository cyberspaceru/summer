package ru.qa.summer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Anchor<T, A extends FieldAnnotator> {
    private final Object parent;
    private final int index;
    private final A annotator;
    private ElementLocator<T> locator;

    public Anchor(@Nullable Object parent, int index, @NotNull A annotator) {
        this.parent = parent;
        this.index = index;
        this.annotator = annotator;
    }

    @NotNull
    protected abstract ElementLocator<T> createElementLocator();

    public Object getParent() {
        return parent;
    }

    public int getIndex() {
        return index;
    }

    @NotNull
    public A getAnnotator() {
        return annotator;
    }

    @NotNull
    public ElementLocator<T> getLocator() {
        if (locator == null) {
            locator = createElementLocator();
        }
        return locator;
    }
}
