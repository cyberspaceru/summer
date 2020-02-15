package ru.qa.summer;

import java.util.List;

public abstract class ElementLocator<T> {
    private final Object parent;
    private final int index;

    public ElementLocator(Object parent, int index) {
        this.parent = parent;
        this.index = index;
    }

    public abstract List<T> locateAll();

    public abstract T locate();

    public abstract boolean exists();

    public abstract int count();

    public Object getParent() {
        return parent;
    }

    public int getIndex() {
        return index;
    }
}
