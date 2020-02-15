package ru.qa.summer.support.data;

import ru.qa.summer.support.exceptions.AccessException;

public interface DataAccessObject<T> {
    T load() throws AccessException;

    void save(T t) throws AccessException;
}
