package ru.qa.summer.support.data;

import com.google.gson.Gson;
import ru.qa.summer.support.Support;
import ru.qa.summer.support.exceptions.AccessException;

public class JsonDataAccessObject<T> implements DataAccessObject<T> {
    private final AccessObject accessibility;
    private final Gson gson;
    private final Class<T> tClass;

    public JsonDataAccessObject(AccessObject accessibility, Class<T> tClass) {
        this(accessibility, null, tClass);
    }

    public JsonDataAccessObject(AccessObject accessibility, Gson gson, Class<T> tClass) {
        this.accessibility = accessibility;
        this.gson = gson == null ? Support.getGson() : gson;
        this.tClass = tClass;
    }

    @Override
    public T load() throws AccessException {
        return gson.fromJson(accessibility.load(), tClass);
    }

    @Override
    public void save(T t) throws AccessException {
        accessibility.save(gson.toJson(t));
    }
}
