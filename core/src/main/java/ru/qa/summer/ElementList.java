package ru.qa.summer;

import org.junit.jupiter.api.Assertions;
import ru.qa.summer.utils.InstantiateCoreUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ElementList<T extends Element, A extends Anchor> {
    private final Map<Integer, T> cache = new HashMap<>();
    private final A anchor;
    private final Class<T> tClass;

    public ElementList(A anchor, Class<T> tClass) {
        this.anchor = anchor;
        this.tClass = tClass;
    }

    @SuppressWarnings("unchecked")
    public List<T> asRegularList() {
        List<T> result = new ArrayList<>();
        int count = this.anchor.getLocator().count();
        for (int i = 0; i < count; i++) {
            result.add(createElement(i));
        }
        return result;
    }

    public void foreach(Consumer<T> consumer) {
        asRegularList().forEach(consumer);
    }

    public void untilTrue(Function<T, Boolean> function) {
        for (T t : asRegularList()) {
            if (function.apply(t)) {
                return;
            }
        }
    }

    public T assertFirst(String message, Predicate<T> predicate) {
        T result = first(predicate);
        Assertions.assertNotNull(result, message);
        return result;
    }

    public T first(Predicate<T> predicate) {
        for (T t : asRegularList()) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public boolean exists() {
        return this.anchor.getLocator().exists();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.anchor.getLocator().count();
    }

    public T get(int index) {
        int count = this.anchor.getLocator().count();
        if (index < count) {
            return createElement(index);
        }
        throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, count));
    }

    private T createElement(int index) {
        T element = cache.size() <= index ? null : cache.get(index);
        if (element == null) {
            element = InstantiateCoreUtils.instantiateElement(tClass, copyAnchor(index));
            cache.put(index, element);
        }
        return element;
    }

    protected abstract A copyAnchor(int index);

    public A getAnchor() {
        return anchor;
    }
}
