package ru.qa.summer.support.data;

import ru.qa.summer.support.exceptions.AccessException;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReferenceAccessObject implements AccessObject {
    private final Supplier<String> toLoad;
    private final Consumer<String> toSave;

    public ReferenceAccessObject(Supplier<String> toLoad, Consumer<String> toSave) {
        this.toLoad = toLoad;
        this.toSave = toSave;
    }

    @Override
    public String load() throws AccessException {
        try {
            return toLoad.get();
        } catch (Exception e) {
            throw new AccessException(e);
        }
    }

    @Override
    public void save(String s) throws AccessException {
        try {
            toSave.accept(s);
        } catch (Exception e) {
            throw new AccessException(e);
        }
    }
}
